package jokatu.components.eventhandlers;

import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.Game;
import jokatu.game.event.AnyGameEventHandler;
import jokatu.game.event.PrivateGameEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Private events should be forwarded to the users they specify.
 * @author Steven Weston
 */
@Component
public class PrivateEventHandler extends AnyGameEventHandler<PrivateGameEvent> {

	private final StoringMessageSender sender;

	@Autowired
	public PrivateEventHandler(StoringMessageSender sender) {
		this.sender = sender;
	}

	@NotNull
	@Override
	protected Class<PrivateGameEvent> getEventClass() {
		return PrivateGameEvent.class;
	}

	@Override
	protected void handleCastGameAndEvent(@NotNull Game game, @NotNull PrivateGameEvent event) {
		event.getPlayers().stream().forEach(
				player -> sender.sendToUser(
						player.getName(),
						"/topic/private.game." + game.getIdentifier(),
						event.getMessage()
				)
		);
	}
}
