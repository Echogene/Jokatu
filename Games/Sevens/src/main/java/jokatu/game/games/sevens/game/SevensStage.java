package jokatu.game.games.sevens.game;

import jokatu.game.MultiInputStage;
import jokatu.game.cards.Card;
import jokatu.game.cards.Cards;
import jokatu.game.cards.Suit;
import jokatu.game.games.sevens.input.CardInputAcceptor;
import jokatu.game.games.sevens.input.SkipInputAcceptor;
import jokatu.game.games.sevens.player.SevensPlayer;
import jokatu.game.status.StandardTextStatus;
import jokatu.game.turn.TurnManager;

import java.util.*;

import static java.text.MessageFormat.format;
import static java.util.Collections.shuffle;
import static jokatu.game.cards.Cards.SEVEN_OF_DIAMONDS;

class SevensStage extends MultiInputStage {

	private final List<SevensPlayer> players;

	SevensStage(Map<String, SevensPlayer> players, StandardTextStatus status, Map<Suit, TreeSet<Card>> playedCards) {
		this.players = assignDealOrder(players);

		dealHands();
		sortHands();

		SevensPlayer startingPlayer = getStartingPlayer();
		status.setText(format("Waiting for {0} to play the seven of diamonds.", startingPlayer));
		TurnManager<SevensPlayer> turnManager = new TurnManager<>(this.players, startingPlayer);
		turnManager.observe(e -> {
			status.setText(format("Waiting for {0} to play a card or pass.", e.getNewPlayer()));
			// Forward the event.
			fireEvent(e);
		});

		addInputAcceptor(new CardInputAcceptor(turnManager, playedCards));
		addInputAcceptor(new SkipInputAcceptor(turnManager));
	}

	private SevensPlayer getStartingPlayer() {
		return players.stream()
				.filter(player -> player.getHand().contains(SEVEN_OF_DIAMONDS))
				.findAny()
				.orElseThrow(IllegalStateException::new);
	}

	private List<SevensPlayer> assignDealOrder(Map<String, SevensPlayer> players) {
		return new ArrayList<>(players.values());
	}

	private void dealHands() {
		List<Card> deck = Cards.getNewDeck();
		shuffle(deck);
		for (int i = 0; i < deck.size(); i++) {
			Card card = deck.get(i);
			SevensPlayer player = players.get(i % players.size());
			player.drawCard(card);
		}
	}

	private void sortHands() {
		players.stream()
				.map(SevensPlayer::getHand)
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
