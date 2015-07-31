package jokatu.game;

import jokatu.game.user.player.Player;

/**
 * A abstract implementation of {@link Game} that has an identifier.
 * @author Steven Weston
 */
public abstract class AbstractGame<P extends Player> implements Game<P> {

	private final GameID identifier;

	protected AbstractGame(long identifier) {
		this.identifier = new GameID();
		this.identifier.setIdentity(identifier);
	}

	@Override
	public GameID getIdentifier() {
		return identifier;
	}
}
