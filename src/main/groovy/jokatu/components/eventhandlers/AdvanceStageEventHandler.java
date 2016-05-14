package jokatu.components.eventhandlers;

import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.Game;
import jokatu.game.event.AbstractEventHandler;
import jokatu.game.event.StageOverEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdvanceStageEventHandler extends AbstractEventHandler<StageOverEvent> {

	private final StoringMessageSender sender;

	@Autowired
	public AdvanceStageEventHandler(StoringMessageSender sender) {
		this.sender = sender;
	}


	@Override
	protected Class<StageOverEvent> handles() {
		return StageOverEvent.class;
	}

	@Override
	protected void handleCastEvent(Game game, StageOverEvent event) {
		game.advanceStage();

		sender.send("/topic/advance.game." + game.getIdentifier(), event.getMessage());
	}
}
