package jokatu.game;

import jokatu.game.event.GameEvent;
import jokatu.game.exception.GameException;
import jokatu.game.input.Input;
import jokatu.game.player.Player;
import jokatu.game.status.Status;
import jokatu.identity.Identifiable;
import ophelia.collections.BaseCollection;
import ophelia.event.observable.AbstractSynchronousObservable;
import ophelia.event.observable.Observable;
import ophelia.exceptions.StackedException;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public abstract class Game<P extends Player>
		extends AbstractSynchronousObservable<GameEvent>
		implements Identifiable<GameID>, Observable<GameEvent> {

	private final GameID identifier;

	protected Game(GameID identifier) {
		this.identifier = identifier;
	}

	@NotNull
	protected abstract Stage getCurrentStage();

	public void accept(@NotNull Input input, @NotNull Player player) throws GameException {
		try {
			getCurrentStage().accept(input, player);
		} catch (StackedException e) {
			throw new GameException(
					getIdentifier(),
					"Input was not accepted by a unique acceptor",
					e
			);
		}
	}

	@NotNull
	@Override
	public GameID getIdentifier() {
		return identifier;
	}

	@NotNull
	public abstract String getGameName();

	@NotNull
	public abstract BaseCollection<P> getPlayers();

	@NotNull
	public abstract Status getStatus();

	// This appears to do nothing, but it is here so that AbstractGameFactory has access to this protected method.
	@Override
	protected void fireEvent(GameEvent event) {
		super.fireEvent(event);
	}

	public boolean hasPlayer(@NotNull Player player) {
		return getPlayers().contains(player);
	}

	public abstract void advanceStage();
}
