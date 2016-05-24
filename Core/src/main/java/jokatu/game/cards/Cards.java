package jokatu.game.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static jokatu.game.cards.Rank.*;
import static jokatu.game.cards.Suit.*;

public interface Cards {

	String PRIVATE_CARD = "\ud83c\udca0";
	
	List<Card> ALL_CARDS = new ArrayList<>(52);

	Card ACE_OF_SPADES   = createCard("\ud83c\udca1", ACE, SPADES);
	Card TWO_OF_SPADES   = createCard("\ud83c\udca2", TWO, SPADES);
	Card THREE_OF_SPADES = createCard("\ud83c\udca3", THREE, SPADES);
	Card FOUR_OF_SPADES  = createCard("\ud83c\udca4", FOUR, SPADES);
	Card FIVE_OF_SPADES  = createCard("\ud83c\udca5", FIVE, SPADES);
	Card SIX_OF_SPADES   = createCard("\ud83c\udca6", SIX, SPADES);
	Card SEVEN_OF_SPADES = createCard("\ud83c\udca7", SEVEN, SPADES);
	Card EIGHT_OF_SPADES = createCard("\ud83c\udca8", EIGHT, SPADES);
	Card NINE_OF_SPADES  = createCard("\ud83c\udca9", NINE, SPADES);
	Card TEN_OF_SPADES   = createCard("\ud83c\udcaa", TEN, SPADES);
	Card JACK_OF_SPADES  = createCard("\ud83c\udcab", JACK, SPADES);
	Card QUEEN_OF_SPADES = createCard("\ud83c\udcad", QUEEN, SPADES);
	Card KING_OF_SPADES  = createCard("\ud83c\udcae", KING, SPADES);

	Card ACE_OF_HEARTS   = createCard("\ud83c\udcb1", ACE, HEARTS);
	Card TWO_OF_HEARTS   = createCard("\ud83c\udcb2", TWO, HEARTS);
	Card THREE_OF_HEARTS = createCard("\ud83c\udcb3", THREE, HEARTS);
	Card FOUR_OF_HEARTS  = createCard("\ud83c\udcb4", FOUR, HEARTS);
	Card FIVE_OF_HEARTS  = createCard("\ud83c\udcb5", FIVE, HEARTS);
	Card SIX_OF_HEARTS   = createCard("\ud83c\udcb6", SIX, HEARTS);
	Card SEVEN_OF_HEARTS = createCard("\ud83c\udcb7", SEVEN, HEARTS);
	Card EIGHT_OF_HEARTS = createCard("\ud83c\udcb8", EIGHT, HEARTS);
	Card NINE_OF_HEARTS  = createCard("\ud83c\udcb9", NINE, HEARTS);
	Card TEN_OF_HEARTS   = createCard("\ud83c\udcba", TEN, HEARTS);
	Card JACK_OF_HEARTS  = createCard("\ud83c\udcbb", JACK, HEARTS);
	Card QUEEN_OF_HEARTS = createCard("\ud83c\udcbd", QUEEN, HEARTS);
	Card KING_OF_HEARTS  = createCard("\ud83c\udcbe", KING, HEARTS);

	Card ACE_OF_DIAMONDS   = createCard("\ud83c\udcc1", ACE, DIAMONDS);
	Card TWO_OF_DIAMONDS   = createCard("\ud83c\udcc2", TWO, DIAMONDS);
	Card THREE_OF_DIAMONDS = createCard("\ud83c\udcc3", THREE, DIAMONDS);
	Card FOUR_OF_DIAMONDS  = createCard("\ud83c\udcc4", FOUR, DIAMONDS);
	Card FIVE_OF_DIAMONDS  = createCard("\ud83c\udcc5", FIVE, DIAMONDS);
	Card SIX_OF_DIAMONDS   = createCard("\ud83c\udcc6", SIX, DIAMONDS);
	Card SEVEN_OF_DIAMONDS = createCard("\ud83c\udcc7", SEVEN, DIAMONDS);
	Card EIGHT_OF_DIAMONDS = createCard("\ud83c\udcc8", EIGHT, DIAMONDS);
	Card NINE_OF_DIAMONDS  = createCard("\ud83c\udcc9", NINE, DIAMONDS);
	Card TEN_OF_DIAMONDS   = createCard("\ud83c\udcca", TEN, DIAMONDS);
	Card JACK_OF_DIAMONDS  = createCard("\ud83c\udccb", JACK, DIAMONDS);
	Card QUEEN_OF_DIAMONDS = createCard("\ud83c\udccd", QUEEN, DIAMONDS);
	Card KING_OF_DIAMONDS  = createCard("\ud83c\udcce", KING, DIAMONDS);

	Card ACE_OF_CLUBS   = createCard("\ud83c\udcd1", ACE, CLUBS);
	Card TWO_OF_CLUBS   = createCard("\ud83c\udcd2", TWO, CLUBS);
	Card THREE_OF_CLUBS = createCard("\ud83c\udcd3", THREE, CLUBS);
	Card FOUR_OF_CLUBS  = createCard("\ud83c\udcd4", FOUR, CLUBS);
	Card FIVE_OF_CLUBS  = createCard("\ud83c\udcd5", FIVE, CLUBS);
	Card SIX_OF_CLUBS   = createCard("\ud83c\udcd6", SIX, CLUBS);
	Card SEVEN_OF_CLUBS = createCard("\ud83c\udcd7", SEVEN, CLUBS);
	Card EIGHT_OF_CLUBS = createCard("\ud83c\udcd8", EIGHT, CLUBS);
	Card NINE_OF_CLUBS  = createCard("\ud83c\udcd9", NINE, CLUBS);
	Card TEN_OF_CLUBS   = createCard("\ud83c\udcda", TEN, CLUBS);
	Card JACK_OF_CLUBS  = createCard("\ud83c\udcdb", JACK, CLUBS);
	Card QUEEN_OF_CLUBS = createCard("\ud83c\udcdd", QUEEN, CLUBS);
	Card KING_OF_CLUBS  = createCard("\ud83c\udcde", KING, CLUBS);

	Map<Suit, Map<Rank, Card>> CARDS_BY_SUIT_AND_RANK = ALL_CARDS.stream()
			.collect(groupingBy(Card::getSuit, toMap(Card::getRank, identity())));

	Map<Rank, Map<Suit, Card>> CARDS_BY_RANK_AND_SUIT = ALL_CARDS.stream()
			.collect(groupingBy(Card::getRank, toMap(Card::getSuit, identity())));

	static Card createCard(String display, Rank rank, Suit suit) {
		Card card = new Card(display, rank, suit);
		ALL_CARDS.add(card);
		return card;
	}
}
