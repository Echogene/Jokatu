package jokatu.components.ui

import jokatu.game.GameID
import jokatu.game.exception.GameException
import jokatu.ui.Dialog
import ophelia.function.ExceptionalConsumer

interface DialogRequestor {

	fun requestDialog(
			dialog: Dialog,
			playerName: String,
			gameId: GameID,
			jsonConsumer: ExceptionalConsumer<Map<String, Any>, GameException>
	)
}
