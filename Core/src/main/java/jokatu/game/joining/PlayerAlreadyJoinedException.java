package jokatu.game.joining;

public class PlayerAlreadyJoinedException extends CannotJoinGameException {

	public PlayerAlreadyJoinedException(String message) {
		super(message);
	}
}
