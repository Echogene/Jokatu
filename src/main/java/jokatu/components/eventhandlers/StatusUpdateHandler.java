package jokatu.components.eventhandlers;

import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.Game;
import jokatu.game.event.AbstractEventHandler;
import jokatu.game.event.StatusUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handle status updates for a game by sending the new status to the game's status channel.
 * @author Steven Weston
 */
@Component
public class StatusUpdateHandler extends AbstractEventHandler<StatusUpdateEvent> {

	private final StoringMessageSender sender;

	@Autowired
	public StatusUpdateHandler(StoringMessageSender sender) {
		this.sender = sender;
	}

	@Override
	protected Class<StatusUpdateEvent> handles() {
		return StatusUpdateEvent.class;
	}

	@Override
	protected void handleCastEvent(Game game, StatusUpdateEvent event) {
		sender.send("/topic/status.game." + game.getIdentifier(), event.getStatus());
	}
}
