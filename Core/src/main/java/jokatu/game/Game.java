package jokatu.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

/**
 * @author Steven Weston
 */
public abstract class Game<P extends Player>
		extends AbstractSynchronousObservable<GameEvent>
		implements Identifiable<GameID>, Observable<GameEvent> {

	private final GameID identifier;

	@Nullable
	protected Stage currentStage;

	protected Game(GameID identifier) {
		this.identifier = identifier;
	}

	public final void accept(@NotNull Input input, @NotNull Player player) throws GameException {
		if (currentStage == null) {
			throw new GameException(identifier, "The game hasn't started yet.");
		}
		try {
			currentStage.accept(input, player);
		} catch (StackedException e) {
			throw new GameException(
					identifier,
					e.getMessage(),
					e
			);
		}
	}

	@NotNull
	@Override
	public final GameID getIdentifier() {
		return identifier;
	}

	@NotNull
	public abstract String getGameName();

	@Nullable
	public abstract P getPlayerByName(@NotNull String name);

	@NotNull
	public abstract Status getStatus();

	public boolean hasPlayer(@NotNull String name) {
		return getPlayerByName(name) != null;
	}

	public final void advanceStage() {
		advanceStageInner();
		assert currentStage != null;
		currentStage.observe(this::fireEvent);
	}

	/**
	 * Set the {@link Game#currentStage} field to the next (not-null) stage.
	 */
	protected abstract void advanceStageInner();

	@JsonIgnore
	@Nullable
	public Stage getCurrentStage() {
		return currentStage;
	}
}
