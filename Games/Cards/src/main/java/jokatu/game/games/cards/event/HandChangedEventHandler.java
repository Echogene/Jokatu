package jokatu.game.games.cards.event;

import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.Game;
import jokatu.game.event.AnyGameEventHandler;
import jokatu.game.games.cards.player.CardPlayer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

/**
 * @author Steven Weston
 */
@Component
public class HandChangedEventHandler extends AnyGameEventHandler<HandChangedEvent> {

	private final StoringMessageSender sender;

	@Autowired
	public HandChangedEventHandler(StoringMessageSender sender) {
		this.sender = sender;
	}

	@NotNull
	@Override
	protected Class<HandChangedEvent> getEventClass() {
		return HandChangedEvent.class;
	}

	@Override
	protected void handleCastGameAndEvent(@NotNull Game game, @NotNull HandChangedEvent event) {
		CardPlayer player = event.getPlayer();
		sender.sendToUser(
				player.getName(),
				format("/topic/hand.game.{0}", game.getIdentifier()),
				player.getHand()
		);

		sender.send(
				format("/topic/handcount.game.{0}.{1}", game.getIdentifier(), player),
				player.getHand().size()
		);
	}
}
