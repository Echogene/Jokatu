package jokatu.game.games.cards.input;

import jokatu.game.cards.Card;
import jokatu.game.cards.Cards;
import jokatu.game.games.cards.player.CardPlayer;
import jokatu.game.input.InputAcceptor;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.set.HashSet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.text.MessageFormat.format;
import static java.util.Collections.shuffle;
import static jokatu.game.cards.Cards.SEVEN_OF_DIAMONDS;

public class CardInputAcceptor extends InputAcceptor<CardInput, CardPlayer> {

	private final List<CardPlayer> players;
	private final StandardTextStatus status;

	private final Set<Card> extremeCards = new HashSet<>();

	private CardPlayer currentPlayer;

	public CardInputAcceptor(Map<String, CardPlayer> players, StandardTextStatus status) {
		this.players = assignDealOrder(players);
		this.status = status;

		dealHands();
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
				status.setText(format("Waiting for the {0} to play the seven of diamonds.", currentPlayer));
			}
		}
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
		if (hasAdjacentExtremeCard(card)) {
			inputter.playCard(card);
		}

		completeTurn();
	}

	private boolean hasAdjacentExtremeCard(@NotNull Card card) {
		if (extremeCards.isEmpty()) {
			return card == SEVEN_OF_DIAMONDS;
		}
		return false;
	}

	private void completeTurn() {
		int i = players.indexOf(currentPlayer);
		currentPlayer = players.get((i + 1) % players.size());
	}
}
