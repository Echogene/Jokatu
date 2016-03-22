package jokatu.game.exception;

import jokatu.game.GameID;

import java.text.MessageFormat;

/**
 * An exception that occurs within a particular game.
 * @author steven
 */
public class GameException extends Exception {

	private final GameID id;

	public GameException(GameID id, String message) {
		super(message);
		this.id = id;
	}

	public GameException(GameID id, String pattern, Object... arguments) {
		this(id, MessageFormat.format(pattern, arguments));
	}

	public GameID getId() {
		return id;
	}
}
