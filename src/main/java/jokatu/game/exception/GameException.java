package jokatu.game.exception;

import jokatu.game.GameID;

/**
 * @author steven
 */
public class GameException extends Exception {

	private final GameID id;

	public GameException(GameID id, String message) {
		super(message);
		this.id = id;
	}

	public GameID getId() {
		return id;
	}
}
