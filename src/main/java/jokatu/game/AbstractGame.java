package jokatu.game;

import jokatu.game.user.player.Player;

import java.util.Collection;

/**
 * @author Steven Weston
 */
public abstract class AbstractGame<P extends Player, C extends Collection<P>> implements Game<P, C> {

	private final GameID identifier;

	protected AbstractGame(long identifier) {
		this.identifier = new GameID(identifier);
	}

	@Override
	public GameID getIdentifier() {
		return identifier;
	}
}
