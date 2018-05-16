package jokatu.components.ui

import jokatu.components.stomp.StoringMessageSender
import jokatu.game.Game
import jokatu.game.GameID
import jokatu.game.exception.GameException
import jokatu.game.player.Player
import jokatu.ui.Dialog
import ophelia.collections.list.UnmodifiableList
import ophelia.function.ExceptionalConsumer
import ophelia.tuple.Pair
import ophelia.util.MapUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.Collections.emptyList
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import java.util.function.Supplier

@Component
class DialogManager
@Autowired constructor(
		private val sender: StoringMessageSender
) : DialogRequestor, DialogResponder {

	private val dialogs = ConcurrentHashMap<DialogResponder.DialogID, ExceptionalConsumer<Map<String, Any>, GameException>>()

	private val playersDialogs = ConcurrentHashMap<Pair<GameID, String>, MutableList<DialogUI>>()

	private val idSupplier = object : Supplier<String> {
		private val id = AtomicLong()

		override fun get(): String {
			return java.lang.Long.toHexString(id.getAndIncrement())
		}
	}

	override fun requestDialog(
			dialog: Dialog,
			playerName: String,
			gameId: GameID,
			jsonConsumer: ExceptionalConsumer<Map<String, Any>, GameException>
	) {
		log.debug("Requesting dialog $dialog for $playerName in game $gameId")
		val dialogId = generateId(gameId)
		val gamePlayerKey = Pair(gameId, playerName)

		val dialogUi = DialogUI(dialog, dialogId.dialogId)

		MapUtils.updateListBasedMap(playersDialogs, gamePlayerKey, dialogUi)

		updatePlayerDialogs(gamePlayerKey)

		dialogs[dialogId] = jsonConsumer
	}

	private fun updatePlayerDialogs(gamePlayerKey: Pair<GameID, String>) {
		val dialogUIs = playersDialogs[gamePlayerKey]!!

		sender.sendToUser(gamePlayerKey.right, "/topic/dialogs.game." + gamePlayerKey.left, dialogUIs)
	}

	private fun generateId(gameId: GameID): DialogResponder.DialogID {
		return DialogResponder.DialogID(gameId, idSupplier.get())
	}

	@Throws(GameException::class)
	override fun respondToDialog(
			dialogId: DialogResponder.DialogID,
			playerName: String,
			json: Map<String, Any>
	) {
		val gamePlayerKey = Pair(dialogId.gameId, playerName)
		val isActuallyPlayersDialog = playersDialogs.getOrDefault(gamePlayerKey, emptyList())
				.stream()
				.anyMatch { ui -> ui.dialogId == dialogId.dialogId }

		val dialog = dialogs[dialogId]
		if (dialog == null || !isActuallyPlayersDialog) {
			throw GameException(
					dialogId.gameId,
					"Dialog with ID '${dialogId.dialogId}' could not be found."
			)
		}
		dialog.accept(json)

		val dialogUIs = playersDialogs[gamePlayerKey] ?: throw GameException(
				dialogId.gameId,
				"You have no dialogs."
		)
		dialogUIs.removeIf { dialogUI -> dialogUI.dialogId == dialogId.dialogId }
		updatePlayerDialogs(gamePlayerKey)
	}

	fun <P : Player> getDialogsForPlayer(
			game: Game<P>,
			player: P
	): UnmodifiableList<DialogUI> {
		return UnmodifiableList(playersDialogs[Pair(game.identifier, player.name)])
	}

	class DialogUI constructor(dialog: Dialog, val dialogId: String) : Dialog(dialog.title, dialog.message, dialog.form, dialog.isCancellable)

	companion object {
		private val log = LoggerFactory.getLogger(DialogManager::class.java)
	}
}
