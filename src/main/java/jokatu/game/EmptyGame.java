package jokatu.game;

import jokatu.game.input.Input;
import jokatu.game.joining.CannotJoinGameException;
import jokatu.game.joining.GameFullException;
import jokatu.game.user.player.Player;
import ophelia.collections.set.EmptySet;

import static jokatu.game.Status.OVER;
import static ophelia.collections.set.EmptySet.emptySet;

/**
 * An zero-player game that does nothing, for testing.
 * @author Steven Weston
 */
public class EmptyGame extends AbstractGame<Player, Input<Player>, EmptySet<Player>, Void> {

	public EmptyGame(long identifier) {
		super(identifier);
	}

	@Override
	public EmptySet<Player> getPlayers() {
		return emptySet();
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

	@Override
	public void accept(Input<Player> input) {
		// Do nothing.
	}
}
