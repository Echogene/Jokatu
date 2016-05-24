package jokatu.game.games.cards.input;

import jokatu.game.cards.Card;
import jokatu.game.cards.Cards;
import jokatu.game.event.StageOverEvent;
import jokatu.game.games.cards.player.CardPlayer;
import jokatu.game.input.InputAcceptor;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.result.PlayerResult;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.set.HashSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static java.text.MessageFormat.format;
import static java.util.Collections.shuffle;
import static java.util.Collections.singleton;
import static jokatu.game.cards.Cards.SEVEN_OF_DIAMONDS;
import static jokatu.game.cards.Rank.SEVEN;
import static jokatu.game.result.Result.WIN;

public class CardInputAcceptor extends InputAcceptor<CardInput, CardPlayer> {

	private final List<CardPlayer> players;
	private final StandardTextStatus status;

	private final Set<Card> extremeCards = new HashSet<>();

	private CardPlayer currentPlayer;

	public CardInputAcceptor(Map<String, CardPlayer> players, StandardTextStatus status) {
		this.players = assignDealOrder(players);
		this.status = status;

		dealHands();
		sortHands();
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
			if (card == SEVEN_OF_DIAMONDS) {
				currentPlayer = player;
				status.setText(format("Waiting for {0} to play the seven of diamonds.", currentPlayer));
			}
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

	@NotNull
	@Override
	protected Class<CardInput> getInputClass() {
		return CardInput.class;
	}

	@NotNull
	@Override
	protected Class<CardPlayer> getPlayerClass() {
		return CardPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull CardInput input, @NotNull CardPlayer inputter) throws Exception {
		Card card = input.getCard();
		if (currentPlayer != inputter) {
			throw new UnacceptableInputException("It''s not your turn!  Wait for {0}.", currentPlayer);
		}
		if (!inputter.getHand().contains(card)) {
			throw new UnacceptableInputException("Stop cheating!  You can't play a card that's not in your hand.");
		}
		Card adjacentExtremeCard = getAdjacentExtremeCard(card);
		if (adjacentExtremeCard == null) {
			throw new UnacceptableInputException("There is nowhere to play that card.");
		}
		if (adjacentExtremeCard.getRank() != SEVEN) {
			extremeCards.remove(adjacentExtremeCard);
		}
		extremeCards.add(card);
		inputter.playCard(card);

		if (inputter.getHand().isEmpty()) {
			fireEvent(new PlayerResult(WIN, singleton(inputter)));
			fireEvent(new StageOverEvent());
		} else {
			completeTurn();
		}
	}

	@Nullable
	private Card getAdjacentExtremeCard(@NotNull Card card) {
		if (card.getRank() == SEVEN) {
			if (extremeCards.isEmpty()) {
				// You can only start the game with the seven of diamonds.
				return card == SEVEN_OF_DIAMONDS ? SEVEN_OF_DIAMONDS : null;
			} else {
				return card;
			}
		}
		return extremeCards.stream()
				.filter(extreme -> isAdjacent(card, extreme))
				.findAny()
				.orElse(null);
	}

	private boolean isAdjacent(Card card, Card extreme) {
		return card.getSuit() == extreme.getSuit()
				&& (card.getRank().getValue() == extreme.getRank().getValue() - 1
						|| card.getRank().getValue() == extreme.getRank().getValue() + 1);
	}

	private void completeTurn() {
		int i = players.indexOf(currentPlayer);
		currentPlayer = players.get((i + 1) % players.size());
		status.setText(format("Waiting for {0} to play a card or pass.", currentPlayer));
	}
}
