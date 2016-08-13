package jokatu.components.ui;

import jokatu.game.GameID;
import jokatu.game.exception.GameException;
import jokatu.identity.Identity;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface DialogResponder {

	String DIALOG_ID = "dialogId";

	void respondToDialog(@NotNull DialogID dialogId, @NotNull String playerName, @NotNull Map<String, Object> json) throws GameException;

	class DialogID implements Identity {
		private final GameID gameId;
		private final String dialogId;

		public DialogID(@NotNull GameID gameId, @NotNull String dialogId) {
			this.gameId = gameId;
			this.dialogId = dialogId;
		}

		@NotNull
		public GameID getGameId() {
			return gameId;
		}

		@NotNull
		public String getDialogId() {
			return dialogId;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			DialogID dialogID = (DialogID) o;

			if (!gameId.equals(dialogID.gameId)) return false;
			return dialogId.equals(dialogID.dialogId);

		}

		@Override
		public int hashCode() {
			int result = gameId.hashCode();
			result = 31 * result + dialogId.hashCode();
			return result;
		}
	}
}
