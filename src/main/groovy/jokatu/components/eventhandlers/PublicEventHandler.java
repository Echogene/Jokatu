package jokatu.components.eventhandlers;

import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.Game;
import jokatu.game.event.AbstractEventHandler;
import jokatu.game.event.PublicGameEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handle public events for a game by sending the event to the game's public channel.
 * @author Steven Weston
 */
@Component
public class PublicEventHandler extends AbstractEventHandler<PublicGameEvent> {

	private final StoringMessageSender sender;

	@Autowired
	public PublicEventHandler(StoringMessageSender sender) {
		this.sender = sender;
	}

	@Override
	protected Class<PublicGameEvent> handles() {
		return PublicGameEvent.class;
	}

	@Override
	public void handleCastEvent(Game game, PublicGameEvent event) {
		sender.send("/topic/public.game." + game.getIdentifier(), event.getMessage());
	}
}
