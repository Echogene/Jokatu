package jokatu.game;

import jokatu.game.event.GameEvent;
import jokatu.game.input.Input;
import jokatu.game.user.player.Player;
import ophelia.collections.BaseCollection;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;

/**
 * A abstract implementation of {@link Game} that has an identifier.
 * @author Steven Weston
 */
public abstract class AbstractGame<
		P extends Player,
		I extends Input,
		E extends GameEvent<P>
>
		extends AbstractSynchronousObservable<E>
		implements Game<P, I, E> {

	private final GameID identifier;

	protected AbstractGame(GameID identifier) {
		this.identifier = identifier;
	}

	@NotNull
	@Override
	public GameID getIdentifier() {
		return identifier;
	}
}
