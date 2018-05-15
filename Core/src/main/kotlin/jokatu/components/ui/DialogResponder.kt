package jokatu.components.ui

import jokatu.game.GameID
import jokatu.game.exception.GameException
import jokatu.identity.Identity

interface DialogResponder {

	@Throws(GameException::class)
	fun respondToDialog(dialogId: DialogID, playerName: String, json: Map<String, Any>)

	class DialogID(val gameId: GameID, val dialogId: String) : Identity {

		override fun equals(other: Any?): Boolean {
			if (this === other) return true
			if (other == null || javaClass != other.javaClass) return false

			val dialogID = other as DialogID?

			return if (gameId != dialogID!!.gameId) false else dialogId == dialogID.dialogId
		}

		override fun hashCode(): Int {
			var result = gameId.hashCode()
			result = 31 * result + dialogId.hashCode()
			return result
		}
	}

	companion object {
		const val DIALOG_ID = "dialogId"
	}
}
