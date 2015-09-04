package jokatu.game;

import jokatu.game.event.GameEvent;
import jokatu.game.input.Input;
import jokatu.game.joining.CannotJoinGameException;
import jokatu.game.joining.PlayerJoinedEvent;
import jokatu.game.player.Player;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;

/**
 * A abstract implementation of {@link Game} that has an identifier.
 * @author Steven Weston
 */
public abstract class AbstractGame<
		P extends Player,
		I extends Input
>
		extends AbstractSynchronousObservable<GameEvent>
		implements Game<P, I> {

	private final GameID identifier;

	protected AbstractGame(GameID identifier) {
		this.identifier = identifier;
	}

	@Override
	public final void join(@NotNull P player) throws CannotJoinGameException {
		joinInternal(player);
		fireEvent(new PlayerJoinedEvent(player));
	}

	protected abstract void joinInternal(@NotNull P player) throws CannotJoinGameException;

	@NotNull
	@Override
	public GameID getIdentifier() {
		return identifier;
	}
}
