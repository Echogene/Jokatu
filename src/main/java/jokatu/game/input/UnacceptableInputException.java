package jokatu.game.input;

import jokatu.game.GameID;
import jokatu.game.exception.GameException;

public class UnacceptableInputException extends GameException {

	public UnacceptableInputException(GameID id, String message) {
		super(id, message);
	}
}
