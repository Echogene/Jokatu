package jokatu.components.controllers.game;

import jokatu.game.Game;
import jokatu.game.event.AbstractEventHandler;
import jokatu.game.event.PublicGameEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Steven Weston
 */
@Component
public class PublicEventHandler extends AbstractEventHandler<PublicGameEvent> {

	private final SimpMessagingTemplate template;

	@Autowired
	public PublicEventHandler(SimpMessagingTemplate template) {
		this.template = template;
	}

	@Override
	protected Class<PublicGameEvent> handles() {
		return PublicGameEvent.class;
	}

	@Override
	public void handleCastEvent(Game game, PublicGameEvent event) {
		template.convertAndSend("/public/game/" + game.getIdentifier(), event.getMessage());
	}
}