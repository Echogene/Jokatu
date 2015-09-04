package jokatu.game.joining;

import jokatu.game.Game;
import jokatu.game.GameID;

/**
 * Thrown to indicate that a {@link Game} already has the maximum number of players.
 */
public class GameFullException extends CannotJoinGameException {

	public GameFullException(GameID id, String message) {
		super(id, message);
	}
}
