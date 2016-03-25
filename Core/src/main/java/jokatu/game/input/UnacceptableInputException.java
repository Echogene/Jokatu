package jokatu.game.input;

import jokatu.game.GameID;
import jokatu.game.exception.GameException;

/**
 * An exception thrown when a user's input is not acceptable.  This could mean that the user sent badly-formed input or
 * they sent it at the wrong time.  If one of these is thrown, it is probably an indication that either the frontend
 * has a bug that sends an input or something is tampering with the user's requests.
 */
public class UnacceptableInputException extends GameException {

	public UnacceptableInputException(GameID id, String message) {
		super(id, message);
	}

	public UnacceptableInputException(GameID id, String message, Throwable cause) {
		super(id, message, cause);
	}

	public UnacceptableInputException(GameID id, String pattern, Object... arguments) {
		super(id, pattern, arguments);
	}
}
