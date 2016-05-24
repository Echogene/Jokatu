package jokatu.game.games.cards.player;

import jokatu.game.cards.Card;
import jokatu.game.event.GameEvent;
import jokatu.game.games.cards.event.CardDrawnEvent;
import jokatu.game.games.cards.event.CardPlayedEvent;
import jokatu.game.player.Player;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CardPlayer extends AbstractSynchronousObservable<GameEvent> implements Player {

	private final String name;
	private List<Card> hand = new ArrayList<>();

	public CardPlayer(String username) {
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

	public void playCard(Card card) {
		boolean removed = hand.remove(card);
		if (removed) {
			fireEvent(new CardPlayedEvent(this, card));
		}
	}
}
