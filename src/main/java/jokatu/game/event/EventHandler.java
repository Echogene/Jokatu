package jokatu.game.event;

import jokatu.game.Game;

/**
 * @author Steven Weston
 */
public abstract class EventHandler<E extends GameEvent> {

	public void handle(Game game, GameEvent e) {
		if (handles().isInstance(e)) {
			handleCastEvent(game, handles().cast(e));
		}
	}

	protected abstract Class<E> handles();

	protected abstract void handleCastEvent(Game game, E event);
}
