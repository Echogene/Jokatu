package jokatu.game.event;

import jokatu.game.Game;

/**
 * An abstract {@link EventHandler} that only handles {@link GameEvent}s of certain class and ignores others.
 * @author Steven Weston
 */
public abstract class AbstractEventHandler<E extends GameEvent> implements EventHandler {

	@Override
	public void handle(Game game, GameEvent e) {
		if (handles().isInstance(e)) {
			handleCastEvent(game, handles().cast(e));
		}
	}

	/**
	 * @return the type of {@link GameEvent}, which would get type-erased, that this {@link EventHandler} handles
	 */
	protected abstract Class<E> handles();

	protected abstract void handleCastEvent(Game game, E event);
}
