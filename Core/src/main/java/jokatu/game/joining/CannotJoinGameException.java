package jokatu.game.joining;

import jokatu.game.GameID;
import jokatu.game.exception.GameException;

/**
 * Thrown to say that a player cannot join a game for some reason.
 */
public class CannotJoinGameException extends GameException {

	public CannotJoinGameException(GameID id, String message) {
		super(id, message);
	}
}
