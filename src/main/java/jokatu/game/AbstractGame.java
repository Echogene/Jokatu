package jokatu.game;

import jokatu.game.input.Input;
import jokatu.game.user.player.Player;

/**
 * A abstract implementation of {@link Game} that has an identifier.
 * @author Steven Weston
 */
public abstract class AbstractGame<P extends Player, I extends Input<P>> implements Game<P, I> {

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
