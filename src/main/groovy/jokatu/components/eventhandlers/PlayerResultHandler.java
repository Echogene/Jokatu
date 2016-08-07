package jokatu.components.eventhandlers;

import jokatu.game.Game;
import jokatu.game.event.SpecificEventHandler;
import jokatu.game.result.PlayerResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * At the end of a game, send the message of the {@link PlayerResult} to a special STOMP destination.
 */
@Component
public class PlayerResultHandler extends SpecificEventHandler<PlayerResult> {
	@NotNull
	@Override
	protected Class<PlayerResult> getEventClass() {
		return PlayerResult.class;
	}

	@Override
	protected void handleCastEvent(@NotNull Game<?> game, @NotNull PlayerResult event) {
		sender.send("/topic/result.game." + game.getIdentifier(), event.getMessage());
	}
}
