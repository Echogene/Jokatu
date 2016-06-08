package jokatu.game.joining;

public class PlayerAlreadyJoinedException extends CannotJoinGameException {

	PlayerAlreadyJoinedException() {
		super("You cannot join the game twice!");
	}
}
