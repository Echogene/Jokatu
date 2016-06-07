package jokatu.game.joining;

public class PlayerAlreadyJoinedException extends CannotJoinGameException {

	public PlayerAlreadyJoinedException() {
		super("You cannot join the game twice!");
	}
}
