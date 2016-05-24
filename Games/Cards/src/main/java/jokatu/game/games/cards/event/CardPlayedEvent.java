package jokatu.game.games.cards.event;

import jokatu.game.cards.Card;
import jokatu.game.event.PublicGameEvent;
import jokatu.game.games.cards.player.CardPlayer;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class CardPlayedEvent implements PublicGameEvent {
	private final String message;

	public CardPlayedEvent(CardPlayer player, Card card) {
		message = MessageFormat.format("{0} played {1}", player, card);
	}

	@NotNull
	@Override
	public String getMessage() {
		return message;
	}
}
