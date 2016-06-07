package jokatu.game.games.sevens.player;

import jokatu.game.cards.Card;
import jokatu.game.event.GameEvent;
import jokatu.game.games.sevens.event.CardDrawnEvent;
import jokatu.game.games.sevens.event.CardPlayedEvent;
import jokatu.game.player.Player;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SevensPlayer extends AbstractSynchronousObservable<GameEvent> implements Player {

	private final String name;
	private final List<Card> hand = new ArrayList<>();

	public SevensPlayer(String username) {
		this.name = username;
	}

	public void drawCard(Card card) {
		hand.add(card);
		fireEvent(new CardDrawnEvent(this, card));
	}

	@NotNull
	@Override
	public String getName() {
		return name;
	}

	public List<Card> getHand() {
		return hand;
	}

	public void playCard(@NotNull Card card) {
		boolean removed = hand.remove(card);
		if (removed) {
			fireEvent(new CardPlayedEvent(this, card));
		}
	}

	@Override
	public String toString() {
		return name;
	}
}
