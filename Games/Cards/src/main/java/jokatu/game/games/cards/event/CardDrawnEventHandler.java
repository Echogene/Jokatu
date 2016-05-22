package jokatu.game.games.cards.event;

import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.Game;
import jokatu.game.event.EventHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

/**
 * @author Steven Weston
 */
@Component
public class CardDrawnEventHandler extends EventHandler<CardDrawnEvent> {

	private final StoringMessageSender sender;

	@Autowired
	public CardDrawnEventHandler(StoringMessageSender sender) {
		this.sender = sender;
	}

	@NotNull
	@Override
	protected Class<CardDrawnEvent> getEventClass() {
		return CardDrawnEvent.class;
	}

	@Override
	protected void handleCastEvent(@NotNull Game game, @NotNull CardDrawnEvent event) {

		event.getPlayers().stream().forEach(
				player -> sender.sendToUser(
						player.getName(),
						format("/topic/hand.game.{0}", game.getIdentifier()),
						player.getHand()
				)
		);
	}
}
