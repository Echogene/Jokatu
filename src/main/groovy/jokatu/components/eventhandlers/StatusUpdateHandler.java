package jokatu.components.eventhandlers;

import jokatu.game.Game;
import jokatu.game.event.AnyGameEventHandler;
import jokatu.game.event.StatusUpdateEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * Handle status updates for a game by sending the new status to the game's status channel.
 * @author Steven Weston
 */
@Component
public class StatusUpdateHandler extends AnyGameEventHandler<StatusUpdateEvent> {

	@NotNull
	@Override
	protected Class<StatusUpdateEvent> getEventClass() {
		return StatusUpdateEvent.class;
	}

	@Override
	protected void handleCastGameAndEvent(@NotNull Game game, @NotNull StatusUpdateEvent event) {
		sender.send("/topic/status.game." + game.getIdentifier(), event.getStatus().getText());
	}
}
