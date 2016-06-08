package jokatu.game.event;

import jokatu.game.Game;
import org.jetbrains.annotations.NotNull;

/**
 * An {@link EventHandler} that accepts any game.
 * @param <E> the type of {@link GameEvent} to accept
 */
public abstract class AnyGameEventHandler<E extends GameEvent> extends EventHandler<Game, E> {

	@NotNull
	@Override
	protected final Class<Game> getGameClass() {
		return Game.class;
	}
}
