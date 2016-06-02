package jokatu.game.games.sevens.event;

import jokatu.game.cards.Suit;
import jokatu.game.event.EventHandler;
import jokatu.game.games.sevens.game.SevensGame;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

/**
 * @author Steven Weston
 */
@Component
public class CardPlayedEventHandler extends EventHandler<SevensGame, CardPlayedEvent> {

	@NotNull
	@Override
	protected Class<CardPlayedEvent> getEventClass() {
		return CardPlayedEvent.class;
	}

	@NotNull
	@Override
	protected Class<SevensGame> getGameClass() {
		return SevensGame.class;
	}

	@Override
	protected void handleCastGameAndEvent(@NotNull SevensGame game, @NotNull CardPlayedEvent event) {
		Suit suit = event.getCard().getSuit();
		sender.send(
				format("/topic/substatus.game.{0}.{1}", game.getIdentifier(), suit),
				game.getCardsOfSuitPlayed(suit)
		);
	}
}
