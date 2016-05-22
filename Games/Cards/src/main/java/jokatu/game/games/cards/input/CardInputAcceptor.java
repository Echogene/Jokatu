package jokatu.game.games.cards.input;

import jokatu.game.cards.Card;
import jokatu.game.cards.StandardCards;
import jokatu.game.games.cards.player.CardPlayer;
import jokatu.game.input.InputAcceptor;
import jokatu.game.status.StandardTextStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.shuffle;

public class CardInputAcceptor extends InputAcceptor<CardInput, CardPlayer> {

	private final List<CardPlayer> players;
	private final StandardTextStatus status;

	private final List<Card> deck;

	public CardInputAcceptor(Map<String, CardPlayer> players, StandardTextStatus status) {
		this.players = assignDealOrder(players);
		// Forward events from players.
		this.players.forEach(player -> player.observe(this::fireEvent));
		this.status = status;

		deck = StandardCards.getNewDeck();
		shuffle(deck);

		dealHands();

		status.setText("Waiting for the owner of the seven of diamonds.");
	}

	private List<CardPlayer> assignDealOrder(Map<String, CardPlayer> players) {
		return new ArrayList<>(players.values());
	}

	private void dealHands() {
		for (int i = 0; i < deck.size(); i++) {
			Card card = deck.get(i);
			CardPlayer player = players.get(i % players.size());
			player.drawCard(card);
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

	}
}
