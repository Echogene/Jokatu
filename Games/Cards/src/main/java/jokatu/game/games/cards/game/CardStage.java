package jokatu.game.games.cards.game;

import jokatu.game.MultiInputStage;
import jokatu.game.cards.Card;
import jokatu.game.cards.Cards;
import jokatu.game.cards.Suit;
import jokatu.game.games.cards.input.CardInputAcceptor;
import jokatu.game.games.cards.player.CardPlayer;
import jokatu.game.status.StandardTextStatus;
import jokatu.game.turn.TurnManager;

import java.util.*;

import static java.text.MessageFormat.format;
import static java.util.Collections.shuffle;
import static jokatu.game.cards.Cards.SEVEN_OF_DIAMONDS;

class CardStage extends MultiInputStage {

	private final List<CardPlayer> players;

	CardStage(Map<String, CardPlayer> players, StandardTextStatus status, Map<Suit, TreeSet<Card>> playedCards) {
		this.players = assignDealOrder(players);

		dealHands();
		sortHands();

		CardPlayer startingPlayer = getStartingPlayer();
		status.setText(format("Waiting for {0} to play the seven of diamonds.", startingPlayer));
		TurnManager<CardPlayer> turnManager = new TurnManager<>(this.players, startingPlayer);
		turnManager.observe(e -> {
			status.setText(format("Waiting for {0} to play a card or pass.", e.getNewPlayer()));
			// Forward the event.
			fireEvent(e);
		});

		addInputAcceptor(new CardInputAcceptor(turnManager, status, playedCards));
	}

	private CardPlayer getStartingPlayer() {
		return players.stream()
				.filter(player -> player.getHand().contains(SEVEN_OF_DIAMONDS))
				.findAny()
				.orElseThrow(IllegalStateException::new);
	}

	private List<CardPlayer> assignDealOrder(Map<String, CardPlayer> players) {
		return new ArrayList<>(players.values());
	}

	private void dealHands() {
		List<Card> deck = Cards.getNewDeck();
		shuffle(deck);
		for (int i = 0; i < deck.size(); i++) {
			Card card = deck.get(i);
			CardPlayer player = players.get(i % players.size());
			player.drawCard(card);
		}
	}

	private void sortHands() {
		players.stream()
				.map(CardPlayer::getHand)
				.forEach(hand -> Collections.sort(hand, (card1, card2) -> {
					int suitComparison = card1.getSuit().compareTo(card2.getSuit());
					if (suitComparison == 0) {
						return card1.getRank().compareTo(card2.getRank());
					} else {
						return suitComparison;
					}
				}));
	}
}
