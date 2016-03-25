package jokatu.game.joining;

/**
 * Thrown to say that a player cannot join a game for some reason.
 */
public class CannotJoinGameException extends Exception {

	public CannotJoinGameException(String message) {
		super(message);
	}
}
