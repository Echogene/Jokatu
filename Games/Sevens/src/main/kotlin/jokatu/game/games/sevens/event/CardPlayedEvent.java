package jokatu.game.games.sevens.event;

import jokatu.game.cards.Card;
import jokatu.game.event.PublicGameEvent;
import jokatu.game.games.sevens.player.SevensPlayer;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class CardPlayedEvent implements PublicGameEvent, HandChangedEvent {

	private final String message;
	private final SevensPlayer player;
	private final Card card;

	public CardPlayedEvent(@NotNull SevensPlayer player, @NotNull Card card) {
		this.player = player;
		this.card = card;
		message = MessageFormat.format("{0} played {1}", player, card.getLabel());
	}

	@NotNull
	@Override
	public String getMessage() {
		return message;
	}

	@NotNull
	@Override
	public SevensPlayer getPlayer() {
		return player;
	}

	@NotNull
	public Card getCard() {
		return card;
	}
}
