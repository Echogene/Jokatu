package jokatu.game.event;

import jokatu.game.Game;
import org.jetbrains.annotations.NotNull;

/**
 * An abstract {@link EventHandler} that only handles {@link GameEvent}s of certain class and ignores others.
 * @author Steven Weston
 */
public abstract class EventHandler<G extends Game, E extends GameEvent> {

	public void handle(@NotNull Game game, @NotNull GameEvent e) {
		if (getEventClass().isInstance(e) && getGameClass().isInstance(game)) {
			handleCastGameAndEvent(getGameClass().cast(game), getEventClass().cast(e));
		}
	}

	/**
	 * @return the type of {@link GameEvent}, which would get type-erased, that this {@link EventHandler} handles
	 */
	@NotNull
	protected abstract Class<E> getEventClass();

	@NotNull
	protected abstract Class<G> getGameClass();

	protected abstract void handleCastGameAndEvent(@NotNull G game, @NotNull E event);
}
