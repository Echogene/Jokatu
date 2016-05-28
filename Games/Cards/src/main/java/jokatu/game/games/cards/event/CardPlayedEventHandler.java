package jokatu.game.games.cards.event;

import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.cards.Suit;
import jokatu.game.event.EventHandler;
import jokatu.game.games.cards.game.CardGame;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

/**
 * @author Steven Weston
 */
@Component
public class CardPlayedEventHandler extends EventHandler<CardGame, CardPlayedEvent> {

	private final StoringMessageSender sender;

	@Autowired
	public CardPlayedEventHandler(StoringMessageSender sender) {
		this.sender = sender;
	}

	@NotNull
	@Override
	protected Class<CardPlayedEvent> getEventClass() {
		return CardPlayedEvent.class;
	}

	@NotNull
	@Override
	protected Class<CardGame> getGameClass() {
		return CardGame.class;
	}

	@Override
	protected void handleCastGameAndEvent(@NotNull CardGame game, @NotNull CardPlayedEvent event) {
		Suit suit = event.getCard().getSuit();
		sender.send(
				format("/topic/substatus.game.{0}.{1}", game.getIdentifier(), suit),
				game.getCardsOfSuitPlayed(suit)
		);
	}
}
