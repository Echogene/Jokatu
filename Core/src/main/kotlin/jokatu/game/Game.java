package jokatu.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jokatu.game.event.GameEvent;
import jokatu.game.exception.GameException;
import jokatu.game.input.Input;
import jokatu.game.player.Player;
import jokatu.game.stage.Stage;
import jokatu.game.stage.machine.StageMachine;
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

/**
 * A game has a collection of {@link Player}s and a current {@link Stage}.
 * @author Steven Weston
 */
public abstract class Game<P extends Player>
		extends AbstractSynchronousObservable<GameEvent>
		implements Identifiable<GameID>, Observable<GameEvent> {

	private final GameID identifier;

	protected final Map<String, P> players = new HashMap<>();

	private final AtomicBoolean currentStageStarted = new AtomicBoolean(false);

	protected StageMachine stageMachine;

	protected Game(@NotNull GameID identifier) {
		this.identifier = identifier;
	}

	public final void accept(@NotNull Input input, @NotNull Player player) throws GameException {
		if (getCurrentStage() == null) {
			throw new GameException(identifier, "The game hasn't started yet.");
		}
		try {
			getCurrentStage().accept(input, player);
		} catch (Exception e) {
			throw new GameException(
					identifier,
					e,
					e.getMessage()
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
		Stage<?> newStage = stageMachine.advance();
		newStage.observe(this::fireEvent);
	}

	/**
	 * Gets the current stage and lazily starts it if it hasn't been started yet.
	 * @return the current stage that has also been started
	 */
	@JsonIgnore
	@Nullable
	public Stage<? extends GameEvent> getCurrentStage() {
		Stage<?> currentStage = stageMachine.getCurrentStage();
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
	public Set<P> getOtherPlayers(@NotNull String name) {
		return players.values().stream()
				.filter(p -> !name.equals(p.getName()))
				.collect(Collectors.toSet());
	}
}
