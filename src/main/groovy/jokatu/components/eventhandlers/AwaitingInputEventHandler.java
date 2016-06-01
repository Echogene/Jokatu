package jokatu.components.eventhandlers;

import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.Game;
import jokatu.game.event.AnyGameEventHandler;
import jokatu.game.input.AwaitingInputEvent;
import jokatu.game.player.Player;
import ophelia.collections.BaseCollection;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AwaitingInputEventHandler extends AnyGameEventHandler<AwaitingInputEvent> {

	private final StoringMessageSender sender;

	@Autowired
	public AwaitingInputEventHandler(StoringMessageSender sender) {
		this.sender = sender;
	}

	@NotNull
	@Override
	protected Class<AwaitingInputEvent> getEventClass() {
		return AwaitingInputEvent.class;
	}

	@Override
	protected void handleCastGameAndEvent(@NotNull Game game, @NotNull AwaitingInputEvent event) {
		((BaseCollection<Player>) game.getPlayers()).stream().forEach(
				player -> sender.sendToUser(
						player.getName(),
						"/topic/awaiting.game." + game.getIdentifier(),
						event.getAwaitingPlayers().contains(player)
				)
		);
	}
}
