package jokatu.game.joining;

import jokatu.game.Game;

/**
 * Thrown to indicate that a {@link Game} already has the maximum number of players.
 */
public class GameFullException extends CannotJoinGameException {

	public GameFullException(String message) {
		super(message);
	}
}
