package jokatu.game;

import jokatu.game.event.GameEvent;
import jokatu.game.exception.GameException;
import jokatu.game.input.Input;
import jokatu.game.player.Player;
import jokatu.game.status.Status;
import jokatu.identity.Identifiable;
import ophelia.event.observable.AbstractSynchronousObservable;
import ophelia.event.observable.Observable;
import ophelia.exceptions.StackedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;

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
					MessageFormat.format(
							"Input was not accepted because {0}", e.getMessage()
					),
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

	@Nullable
	public abstract P getPlayerByName(@NotNull String name);

	@NotNull
	public abstract Status getStatus();

	public boolean hasPlayer(@NotNull Player player) {
		return getPlayerByName(player.getName()) != null;
	}

	public abstract void advanceStage();
}
