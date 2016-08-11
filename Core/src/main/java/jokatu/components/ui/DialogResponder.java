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
	}
}
