package jokatu.game.games.sevens.event;

import jokatu.game.cards.Card;
import jokatu.game.event.PrivateGameEvent;
import jokatu.game.games.sevens.player.SevensPlayer;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.Singleton;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class CardDrawnEvent implements PrivateGameEvent, HandChangedEvent {

	private final SevensPlayer player;
	private final Card drawn;

	public CardDrawnEvent(SevensPlayer player, Card drawn) {
		this.player = player;
		this.drawn = drawn;
	}

	@NotNull
	@Override
	public String getMessage() {
		return MessageFormat.format("You drew {0}.", drawn.getLabel());
	}

	@NotNull
	@Override
	public BaseCollection<SevensPlayer> getPlayers() {
		return new Singleton<>(player);
	}

	@NotNull
	@Override
	public SevensPlayer getPlayer() {
		return player;
	}
}
