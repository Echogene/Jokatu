package jokatu.components.eventhandlers;

import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.Game;
import jokatu.game.event.EventHandler;
import jokatu.game.event.StatusUpdateEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handle status updates for a game by sending the new status to the game's status channel.
 * @author Steven Weston
 */
@Component
public class StatusUpdateHandler extends EventHandler<StatusUpdateEvent> {

	private final StoringMessageSender sender;

	@Autowired
	public StatusUpdateHandler(StoringMessageSender sender) {
		this.sender = sender;
	}

	@NotNull
	@Override
	protected Class<StatusUpdateEvent> getEventClass() {
		return StatusUpdateEvent.class;
	}

	@Override
	protected void handleCastEvent(@NotNull Game game, @NotNull StatusUpdateEvent event) {
		sender.send("/topic/status.game." + game.getIdentifier(), event.getStatus().getText());
	}
}
