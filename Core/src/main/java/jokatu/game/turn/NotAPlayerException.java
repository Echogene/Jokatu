package jokatu.game.turn;

public class NotAPlayerException extends Exception {
	public NotAPlayerException() {
		super("You can't input to a game you're not playing.");
	}
}
