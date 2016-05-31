package jokatu.game.games.cards.input;

import jokatu.game.cards.Card;
import jokatu.game.cards.Suit;
import jokatu.game.event.StageOverEvent;
import jokatu.game.games.cards.player.CardPlayer;
import jokatu.game.input.AnyEventInputAcceptor;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.result.PlayerResult;
import jokatu.game.turn.TurnManager;
import ophelia.collections.set.HashSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Collections.singleton;
import static jokatu.game.cards.Cards.SEVEN_OF_DIAMONDS;
import static jokatu.game.cards.Rank.SEVEN;
import static jokatu.game.result.Result.WIN;

public class CardInputAcceptor extends AnyEventInputAcceptor<CardInput, CardPlayer> {

	private TurnManager<CardPlayer> turnManager;

	private final Set<Card> extremeCards = new HashSet<>();

	private final Map<Suit, TreeSet<Card>> playedCards;

	public CardInputAcceptor(TurnManager<CardPlayer> turnManager, Map<Suit, TreeSet<Card>> playedCards) {
		this.turnManager = turnManager;
		this.playedCards = playedCards;
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
		turnManager.assertCurrentPlayer(inputter);

		Card card = input.getCard();
		if (!inputter.getHand().contains(card)) {
			throw new UnacceptableInputException("Stop cheating!  You can't play a card that's not in your hand.");
		}
		Card adjacentExtremeCard = getAdjacentExtremeCard(card);
		if (adjacentExtremeCard == null) {
			throw new UnacceptableInputException("There is nowhere to play that card.");
		}
		playCard(inputter, card, adjacentExtremeCard);

		if (inputter.getHand().isEmpty()) {
			fireEvent(new PlayerResult(WIN, singleton(inputter)));
			fireEvent(new StageOverEvent());
		} else {
			turnManager.next();
		}
	}

	private void playCard(@NotNull CardPlayer inputter, Card card, Card adjacentExtremeCard) {
		if (adjacentExtremeCard.getRank() != SEVEN) {
			extremeCards.remove(adjacentExtremeCard);
		}
		extremeCards.add(card);
		playedCards.get(card.getSuit()).add(card);
		inputter.playCard(card);
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
}
