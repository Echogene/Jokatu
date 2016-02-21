package jokatu.components.eventhandlers;

import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.Game;
import jokatu.game.event.AbstractEventHandler;
import jokatu.game.event.PrivateGameEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Private events should be forwarded to the users they specify.
 * @author Steven Weston
 */
@Component
public class PrivateEventHandler extends AbstractEventHandler<PrivateGameEvent> {

	private final StoringMessageSender sender;

	@Autowired
	public PrivateEventHandler(StoringMessageSender sender) {
		this.sender = sender;
	}

	@Override
	protected Class<PrivateGameEvent> handles() {
		return PrivateGameEvent.class;
	}

	@Override
	protected void handleCastEvent(Game game, PrivateGameEvent event) {
		event.getPlayers().stream().forEach(
				player -> sender.sendToUser(
						player.getName(),
						"/topic/private.game." + game.getIdentifier(),
						event.getMessage()
				)
		);
	}
}