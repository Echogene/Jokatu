package jokatu.components.eventhandlers;

import jokatu.game.Game;
import jokatu.game.event.SpecificEventHandler;
import jokatu.game.input.AwaitingInputEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class AwaitingInputEventHandler extends SpecificEventHandler<AwaitingInputEvent> {

	@NotNull
	@Override
	protected Class<AwaitingInputEvent> getEventClass() {
		return AwaitingInputEvent.class;
	}

	@Override
	protected void handleCastEvent(@NotNull Game<?> game, @NotNull AwaitingInputEvent event) {
		game.getPlayers().stream().forEach(
				player -> sender.sendToUser(
						player.getName(),
						"/topic/awaiting.game." + game.getIdentifier(),
						event.getAwaitingPlayers().contains(player)
				)
		);
	}
}
