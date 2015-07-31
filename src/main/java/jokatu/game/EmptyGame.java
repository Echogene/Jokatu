package jokatu.game;

import jokatu.game.joining.CannotJoinGameException;
import jokatu.game.joining.GameFullException;
import jokatu.game.user.player.Player;

import java.util.Collections;
import java.util.Set;

import static jokatu.game.Status.OVER;

/**
 * An zero-player game that does nothing, for testing.
 * @author Steven Weston
 */
public class EmptyGame extends AbstractGame<Player, Set<Player>> {

	public EmptyGame(long identifier) {
		super(identifier);
	}

	@Override
	public Set<Player> getPlayers() {
		return Collections.emptySet();
	}

	@Override
	public void join(Player player) throws CannotJoinGameException {
		throw new GameFullException("An empty game cannot have any players");
	}

	@Override
	public Status getStatus() {
		// An empty game is always over.
		return OVER;
	}
}
