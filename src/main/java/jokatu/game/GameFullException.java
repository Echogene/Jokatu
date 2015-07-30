package jokatu.game;

/**
 * Thrown to indicate that a {@link Game} already has the maximum number of players.
 */
public class GameFullException extends Exception {

	public GameFullException(String message) {
		super(message);
	}
}
