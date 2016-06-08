package jokatu.game.event;

import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.Game;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Listens to events but ignores the ones that aren't for the game and the event type it specifies.
 * @param <G> the type of {@link Game} to accept
 * @param <E> the type of {@link GameEvent} to accept
 */
@Component
public abstract class AbstractEventHandler<G extends Game, E extends GameEvent> implements EventHandler {

	@Autowired
	protected StoringMessageSender sender;

	@Override
	public final void handle(@NotNull Game<?> game, @NotNull GameEvent e) {
		if (getEventClass().isInstance(e) && getGameClass().isInstance(game)) {
			handleCastGameAndEvent(getGameClass().cast(game), getEventClass().cast(e));
		}
	}

	/**
	 * @return the type of {@link GameEvent}, which would get type-erased, that this {@link AbstractEventHandler} handles
	 */
	@NotNull
	protected abstract Class<E> getEventClass();

	@NotNull
	protected abstract Class<G> getGameClass();

	protected abstract void handleCastGameAndEvent(@NotNull G game, @NotNull E event);
}
