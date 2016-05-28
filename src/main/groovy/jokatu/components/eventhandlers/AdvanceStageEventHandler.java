package jokatu.components.eventhandlers;

import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.Game;
import jokatu.game.event.AnyGameEventHandler;
import jokatu.game.event.StageOverEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdvanceStageEventHandler extends AnyGameEventHandler<StageOverEvent> {

	private final StoringMessageSender sender;

	@Autowired
	public AdvanceStageEventHandler(StoringMessageSender sender) {
		this.sender = sender;
	}

	@NotNull
	@Override
	protected Class<StageOverEvent> getEventClass() {
		return StageOverEvent.class;
	}

	@Override
	protected void handleCastGameAndEvent(@NotNull Game game, @NotNull StageOverEvent event) {
		game.advanceStage();

		sender.send("/topic/advance.game." + game.getIdentifier(), event.getMessage());
	}
}
