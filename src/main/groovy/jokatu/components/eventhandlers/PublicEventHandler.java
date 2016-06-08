package jokatu.components.eventhandlers;

import jokatu.game.Game;
import jokatu.game.event.PublicGameEvent;
import jokatu.game.event.SpecificEventHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * Handle public events for a game by sending the event to the game's public channel.
 * @author Steven Weston
 */
@Component
public class PublicEventHandler extends SpecificEventHandler<PublicGameEvent> {

	@NotNull
	@Override
	protected Class<PublicGameEvent> getEventClass() {
		return PublicGameEvent.class;
	}

	@Override
	public void handleCastEvent(@NotNull Game<?> game, @NotNull PublicGameEvent event) {
		sender.send("/topic/public.game." + game.getIdentifier(), event.getMessage());
	}
}
