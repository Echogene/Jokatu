package jokatu.game.joining;

/**
 * Thrown to say that a player cannot join a game for some reason.
 */
class CannotJoinGameException extends Exception {

	CannotJoinGameException(String message) {
		super(message);
	}
}
