package jokatu.components.controllers.game;

import jokatu.game.Game;
import jokatu.game.event.PrivateGameEvent;
import jokatu.game.event.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Steven Weston
 */
@Component
public class PrivateEventHandler extends EventHandler<PrivateGameEvent> {

	private final SimpMessagingTemplate template;

	@Autowired
	public PrivateEventHandler(SimpMessagingTemplate template) {
		this.template = template;
	}

	@Override
	protected Class<PrivateGameEvent> handles() {
		return PrivateGameEvent.class;
	}

	@Override
	protected void handleCastEvent(Game game, PrivateGameEvent event) {
		event.getPlayers().stream().forEach(
				player -> template.convertAndSendToUser(
						player.getName(),
						"/game/" + game.getIdentifier(),
						event.getMessage()
				)
		);
	}
}
