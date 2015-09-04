package jokatu.game.joining;

import jokatu.game.GameID;
import jokatu.game.exception.GameException;

public class CannotJoinGameException extends GameException {

	public CannotJoinGameException(GameID id, String message) {
		super(id, message);
	}
}
