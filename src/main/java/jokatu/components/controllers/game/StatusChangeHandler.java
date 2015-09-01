package jokatu.components.controllers.game;

import jokatu.game.Game;
import jokatu.game.event.StatusChangeEvent;
import jokatu.game.event.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Steven Weston
 */
@Component
public class StatusChangeHandler extends EventHandler<StatusChangeEvent> {

	private final SimpMessagingTemplate template;

	@Autowired
	public StatusChangeHandler(SimpMessagingTemplate template) {
		this.template = template;
	}

	@Override
	protected Class<StatusChangeEvent> handles() {
		return StatusChangeEvent.class;
	}

	@Override
	protected void handleCastEvent(Game game, StatusChangeEvent event) {
		template.convertAndSend("/status/game/" + game.getIdentifier(), event.getStatus());
	}
}
