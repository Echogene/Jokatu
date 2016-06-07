package jokatu.components.eventhandlers;

import jokatu.game.Game;
import jokatu.game.event.AnyGameEventHandler;
import jokatu.game.result.PlayerResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class PlayerResultHandler extends AnyGameEventHandler<PlayerResult> {
	@NotNull
	@Override
	protected Class<PlayerResult> getEventClass() {
		return PlayerResult.class;
	}

	@Override
	protected void handleCastGameAndEvent(@NotNull Game game, @NotNull PlayerResult event) {
		sender.send("/topic/result.game." + game.getIdentifier(), event.getMessage());
	}
}
