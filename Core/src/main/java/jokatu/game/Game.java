package jokatu.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jokatu.game.event.GameEvent;
import jokatu.game.exception.GameException;
import jokatu.game.input.Input;
import jokatu.game.player.Player;
import jokatu.game.stage.Stage;
import jokatu.identity.Identifiable;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.event.observable.AbstractSynchronousObservable;
import ophelia.event.observable.Observable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static ophelia.util.FunctionUtils.not;

/**
 * @author Steven Weston
 */
public abstract class Game<P extends Player>
		extends AbstractSynchronousObservable<GameEvent>
		implements Identifiable<GameID>, Observable<GameEvent> {

	private final GameID identifier;

	protected final Map<String, P> players = new HashMap<>();

	private final AtomicBoolean currentStageStarted = new AtomicBoolean(false);

	@Nullable
	protected Stage<? extends GameEvent> currentStage;

	protected Game(@NotNull GameID identifier) {
		this.identifier = identifier;
	}

	public final void accept(@NotNull Input input, @NotNull Player player) throws GameException {
		if (currentStage == null) {
			throw new GameException(identifier, "The game hasn't started yet.");
		}
		try {
			currentStage.accept(input, player);
		} catch (Exception e) {
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
	public P getPlayerByName(@NotNull String name) {
		return players.get(name);
	}

	public boolean hasPlayer(@NotNull String name) {
		return getPlayerByName(name) != null;
	}

	public final void advanceStage() {
		currentStageStarted.set(false);
		advanceStageInner();
		assert currentStage != null;
		currentStage.observe(this::fireEvent);
	}

	/**
	 * Set the {@link Game#currentStage} field to the next (not-null) stage.
	 */
	protected abstract void advanceStageInner();

	/**
	 * Gets the current stage and lazily starts it if it hasn't been started yet.
	 * @return the current stage that has also been started
	 */
	@JsonIgnore
	@Nullable
	public Stage<? extends GameEvent> getCurrentStage() {
		if (!currentStageStarted.getAndSet(true) && currentStage != null) {
			currentStage.start();
		}
		return currentStage;
	}

	@NotNull
	public UnmodifiableSet<P> getPlayers() {
		return new UnmodifiableSet<>(players.values());
	}

	@NotNull
	public Set<String> getOtherPlayersNames(@NotNull String name) {
		return players.values().stream()
				.map(Player::getName)
				.filter(not(name::equals))
				.collect(Collectors.toSet());
	}
}
