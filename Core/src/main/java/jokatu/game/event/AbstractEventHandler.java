package jokatu.game.event;

import jokatu.game.Game;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * Listens to events but ignores the ones that aren't for the game and the event type it specifies.
 * @param <G> the type of {@link Game} to accept
 * @param <E> the type of {@link GameEvent} to accept
 */
@Component
public abstract class AbstractEventHandler<G extends Game, E extends GameEvent> extends SpecificEventHandler<E> {

	@Override
	protected final void handleCastEvent(@NotNull Game<?> game, @NotNull E event) {
		if (getGameClass().isInstance(game)) {
			handleCastGameAndEvent(getGameClass().cast(game), event);
		}
	}

	@NotNull
	protected abstract Class<G> getGameClass();

	protected abstract void handleCastGameAndEvent(@NotNull G game, @NotNull E event);
}
