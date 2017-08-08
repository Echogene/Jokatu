package jokatu.components.eventhandlers;

import jokatu.game.Game;
import jokatu.game.event.SpecificEventHandler;
import jokatu.game.event.StageOverEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * When a stage says it is over, move the game onto the next stage.
 */
@Component
public class AdvanceStageEventHandler extends SpecificEventHandler<StageOverEvent> {

	@NotNull
	@Override
	protected Class<StageOverEvent> getEventClass() {
		return StageOverEvent.class;
	}

	@Override
	protected void handleCastEvent(@NotNull Game<?> game, @NotNull StageOverEvent event) {
		game.advanceStage();

		sender.send("/topic/advance.game." + game.getIdentifier(), true);
	}
}
