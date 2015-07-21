package jokatu.game;

import jokatu.game.user.player.Player;

import java.util.Collections;
import java.util.Set;

/**
 * An zero-player game that does nothing for testing.
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
}
