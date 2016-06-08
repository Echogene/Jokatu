package jokatu.game.joining;

/**
 * Thrown when a player tries to join the same game twice.
 */
public class PlayerAlreadyJoinedException extends CannotJoinGameException {

	PlayerAlreadyJoinedException() {
		super("You cannot join the game twice!");
	}
}
