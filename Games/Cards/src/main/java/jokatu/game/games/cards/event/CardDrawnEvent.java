package jokatu.game.games.cards.event;

import jokatu.game.cards.Card;
import jokatu.game.event.PrivateGameEvent;
import jokatu.game.games.cards.player.CardPlayer;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.Singleton;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class CardDrawnEvent implements PrivateGameEvent {

	private final CardPlayer player;
	private final Card drawn;

	public CardDrawnEvent(CardPlayer player, Card drawn) {
		this.player = player;
		this.drawn = drawn;
	}

	@NotNull
	@Override
	public String getMessage() {
		return MessageFormat.format("You drew {0}.", drawn);
	}

	@NotNull
	@Override
	public BaseCollection<CardPlayer> getPlayers() {
		return new Singleton<>(player);
	}
}
