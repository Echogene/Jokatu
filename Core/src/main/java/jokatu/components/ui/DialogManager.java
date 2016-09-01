package jokatu.components.ui;

import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.GameID;
import jokatu.game.exception.GameException;
import jokatu.ui.Dialog;
import ophelia.function.ExceptionalConsumer;
import ophelia.tuple.Pair;
import ophelia.util.MapUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@Component
public class DialogManager implements DialogRequestor, DialogResponder {

	private final StoringMessageSender sender;

	private final Map<DialogID, ExceptionalConsumer<Map<String, Object>, GameException>> dialogs = new ConcurrentHashMap<>();

	private final Map<Pair<GameID, String>, List<DialogUI>> playersDialogs = new ConcurrentHashMap<>();

	private final Supplier<String> idSupplier = new Supplier<String>() {
		private final AtomicLong id = new AtomicLong();

		@NotNull
		@Override
		public String get() {
			return Long.toHexString(id.getAndIncrement());
		}
	};

	@Autowired
	public DialogManager(StoringMessageSender sender) {
		this.sender = sender;
	}

	@Override
	public void requestDialog(
			@NotNull final Dialog dialog,
			@NotNull String playerName,
			@NotNull GameID gameId,
			@NotNull ExceptionalConsumer<Map<String, Object>, GameException> jsonConsumer
	) {
		DialogID dialogId = generateId(gameId);
		Pair<GameID, String> gamePlayerKey = new Pair<>(gameId, playerName);

		DialogUI dialogUi = new DialogUI(dialog, dialogId.getDialogId());

		MapUtils.updateListBasedMap(playersDialogs, gamePlayerKey, dialogUi);

		updatePlayerDialogs(gamePlayerKey);

		dialogs.put(dialogId, jsonConsumer);
	}

	private void updatePlayerDialogs(@NotNull Pair<GameID, String> gamePlayerKey) {
		List<DialogUI> dialogUIs = playersDialogs.get(gamePlayerKey);
		assert dialogUIs != null;

		sender.sendToUser(gamePlayerKey.getRight(), "/topic/dialogs.game." + gamePlayerKey.getLeft(), dialogUIs);
	}

	@NotNull
	private DialogID generateId(@NotNull GameID gameId) {
		return new DialogID(gameId, idSupplier.get());
	}

	@Override
	public void respondToDialog(
			@NotNull DialogID dialogId,
			@NotNull String playerName,
			@NotNull Map<String, Object> json
	) throws GameException {
		ExceptionalConsumer<Map<String, Object>, GameException> dialog = dialogs.get(dialogId);
		if (dialog == null) {
			throw new GameException(
					dialogId.getGameId(),
					"Dialog with ID ''{0}'' could not be found.",
					dialogId.getDialogId()
			);
		}
		dialog.accept(json);

		Pair<GameID, String> gamePlayerKey = new Pair<>(dialogId.getGameId(), playerName);
		List<DialogUI> dialogUIs = playersDialogs.get(gamePlayerKey);
		if (dialogUIs == null) {
			throw new GameException(
					dialogId.getGameId(),
					"You have no dialogs.",
					dialogId.getDialogId()
			);
		}
		dialogUIs.removeIf(dialogUI -> dialogUI.getDialogId().equals(dialogId.getDialogId()));
		updatePlayerDialogs(gamePlayerKey);
	}

	private static class DialogUI extends Dialog {
		private final String dialogId;

		private DialogUI(@NotNull Dialog dialog, String dialogId) {
			super(dialog.getTitle(), dialog.getMessage(), dialog.getForm(), dialog.isCancellable());
			this.dialogId = dialogId;
		}

		@NotNull
		public String getDialogId() {
			return dialogId;
		}
	}
}
