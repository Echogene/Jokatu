package jokatu.components.eventhandlers;

import jokatu.game.Game;
import jokatu.game.event.AbstractEventHandler;
import jokatu.game.event.StageOverEvent;
import org.springframework.stereotype.Component;

@Component
public class AdvanceStageEventHandler extends AbstractEventHandler<StageOverEvent> {
	@Override
	protected Class<StageOverEvent> handles() {
		return StageOverEvent.class;
	}

	@Override
	protected void handleCastEvent(Game game, StageOverEvent event) {
		game.advanceStage();
	}
}
