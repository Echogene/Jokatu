package jokatu.game.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static jokatu.game.cards.Rank.*;
import static jokatu.game.cards.Suit.*;

/**
 * Contains constants for all of the 52 standard playingcards.
 */
@SuppressWarnings("unused")
public abstract class Cards {

	public static final String PRIVATE_CARD = "\ud83c\udca0";
	
	private static final List<Card> ALL_CARDS = new ArrayList<>(52);

	public static final Card ACE_OF_SPADES   = createCard("\ud83c\udca1", ACE, SPADES);
	public static final Card TWO_OF_SPADES   = createCard("\ud83c\udca2", TWO, SPADES);
	public static final Card THREE_OF_SPADES = createCard("\ud83c\udca3", THREE, SPADES);
	public static final Card FOUR_OF_SPADES  = createCard("\ud83c\udca4", FOUR, SPADES);
	public static final Card FIVE_OF_SPADES  = createCard("\ud83c\udca5", FIVE, SPADES);
	public static final Card SIX_OF_SPADES   = createCard("\ud83c\udca6", SIX, SPADES);
	public static final Card SEVEN_OF_SPADES = createCard("\ud83c\udca7", SEVEN, SPADES);
	public static final Card EIGHT_OF_SPADES = createCard("\ud83c\udca8", EIGHT, SPADES);
	public static final Card NINE_OF_SPADES  = createCard("\ud83c\udca9", NINE, SPADES);
	public static final Card TEN_OF_SPADES   = createCard("\ud83c\udcaa", TEN, SPADES);
	public static final Card JACK_OF_SPADES  = createCard("\ud83c\udcab", JACK, SPADES);
	public static final Card QUEEN_OF_SPADES = createCard("\ud83c\udcad", QUEEN, SPADES);
	public static final Card KING_OF_SPADES  = createCard("\ud83c\udcae", KING, SPADES);

	public static final Card ACE_OF_HEARTS   = createCard("\ud83c\udcb1", ACE, HEARTS);
	public static final Card TWO_OF_HEARTS   = createCard("\ud83c\udcb2", TWO, HEARTS);
	public static final Card THREE_OF_HEARTS = createCard("\ud83c\udcb3", THREE, HEARTS);
	public static final Card FOUR_OF_HEARTS  = createCard("\ud83c\udcb4", FOUR, HEARTS);
	public static final Card FIVE_OF_HEARTS  = createCard("\ud83c\udcb5", FIVE, HEARTS);
	public static final Card SIX_OF_HEARTS   = createCard("\ud83c\udcb6", SIX, HEARTS);
	public static final Card SEVEN_OF_HEARTS = createCard("\ud83c\udcb7", SEVEN, HEARTS);
	public static final Card EIGHT_OF_HEARTS = createCard("\ud83c\udcb8", EIGHT, HEARTS);
	public static final Card NINE_OF_HEARTS  = createCard("\ud83c\udcb9", NINE, HEARTS);
	public static final Card TEN_OF_HEARTS   = createCard("\ud83c\udcba", TEN, HEARTS);
	public static final Card JACK_OF_HEARTS  = createCard("\ud83c\udcbb", JACK, HEARTS);
	public static final Card QUEEN_OF_HEARTS = createCard("\ud83c\udcbd", QUEEN, HEARTS);
	public static final Card KING_OF_HEARTS  = createCard("\ud83c\udcbe", KING, HEARTS);

	public static final Card ACE_OF_DIAMONDS   = createCard("\ud83c\udcc1", ACE, DIAMONDS);
	public static final Card TWO_OF_DIAMONDS   = createCard("\ud83c\udcc2", TWO, DIAMONDS);
	public static final Card THREE_OF_DIAMONDS = createCard("\ud83c\udcc3", THREE, DIAMONDS);
	public static final Card FOUR_OF_DIAMONDS  = createCard("\ud83c\udcc4", FOUR, DIAMONDS);
	public static final Card FIVE_OF_DIAMONDS  = createCard("\ud83c\udcc5", FIVE, DIAMONDS);
	public static final Card SIX_OF_DIAMONDS   = createCard("\ud83c\udcc6", SIX, DIAMONDS);
	public static final Card SEVEN_OF_DIAMONDS = createCard("\ud83c\udcc7", SEVEN, DIAMONDS);
	public static final Card EIGHT_OF_DIAMONDS = createCard("\ud83c\udcc8", EIGHT, DIAMONDS);
	public static final Card NINE_OF_DIAMONDS  = createCard("\ud83c\udcc9", NINE, DIAMONDS);
	public static final Card TEN_OF_DIAMONDS   = createCard("\ud83c\udcca", TEN, DIAMONDS);
	public static final Card JACK_OF_DIAMONDS  = createCard("\ud83c\udccb", JACK, DIAMONDS);
	public static final Card QUEEN_OF_DIAMONDS = createCard("\ud83c\udccd", QUEEN, DIAMONDS);
	public static final Card KING_OF_DIAMONDS  = createCard("\ud83c\udcce", KING, DIAMONDS);

	public static final Card ACE_OF_CLUBS   = createCard("\ud83c\udcd1", ACE, CLUBS);
	public static final Card TWO_OF_CLUBS   = createCard("\ud83c\udcd2", TWO, CLUBS);
	public static final Card THREE_OF_CLUBS = createCard("\ud83c\udcd3", THREE, CLUBS);
	public static final Card FOUR_OF_CLUBS  = createCard("\ud83c\udcd4", FOUR, CLUBS);
	public static final Card FIVE_OF_CLUBS  = createCard("\ud83c\udcd5", FIVE, CLUBS);
	public static final Card SIX_OF_CLUBS   = createCard("\ud83c\udcd6", SIX, CLUBS);
	public static final Card SEVEN_OF_CLUBS = createCard("\ud83c\udcd7", SEVEN, CLUBS);
	public static final Card EIGHT_OF_CLUBS = createCard("\ud83c\udcd8", EIGHT, CLUBS);
	public static final Card NINE_OF_CLUBS  = createCard("\ud83c\udcd9", NINE, CLUBS);
	public static final Card TEN_OF_CLUBS   = createCard("\ud83c\udcda", TEN, CLUBS);
	public static final Card JACK_OF_CLUBS  = createCard("\ud83c\udcdb", JACK, CLUBS);
	public static final Card QUEEN_OF_CLUBS = createCard("\ud83c\udcdd", QUEEN, CLUBS);
	public static final Card KING_OF_CLUBS  = createCard("\ud83c\udcde", KING, CLUBS);

	public static final Map<String, Card> CARDS_BY_DISPLAY = ALL_CARDS.stream()
			.collect(toMap(Card::toString, identity()));

	public static final Map<Suit, Map<Rank, Card>> CARDS_BY_SUIT_AND_RANK = ALL_CARDS.stream()
			.collect(groupingBy(Card::getSuit, toMap(Card::getRank, identity())));

	public static final Map<Rank, Map<Suit, Card>> CARDS_BY_RANK_AND_SUIT = ALL_CARDS.stream()
			.collect(groupingBy(Card::getRank, toMap(Card::getSuit, identity())));

	private static Card createCard(String display, Rank rank, Suit suit) {
		Card card = new Card(display, rank, suit);
		ALL_CARDS.add(card);
		return card;
	}

	public static List<Card> getNewDeck() {
		return new ArrayList<>(ALL_CARDS);
	}
}
