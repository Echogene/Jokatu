package jokatu.game.cards;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public interface Cards {

	String PRIVATE_CARD = "\ud83c\udca0";

	Card ACE_OF_SPADES   = new Card("\ud83c\udca1");
	Card TWO_OF_SPADES   = new Card("\ud83c\udca2");
	Card THREE_OF_SPADES = new Card("\ud83c\udca3");
	Card FOUR_OF_SPADES  = new Card("\ud83c\udca4");
	Card FIVE_OF_SPADES  = new Card("\ud83c\udca5");
	Card SIX_OF_SPADES   = new Card("\ud83c\udca6");
	Card SEVEN_OF_SPADES = new Card("\ud83c\udca7");
	Card EIGHT_OF_SPADES = new Card("\ud83c\udca8");
	Card NINE_OF_SPADES  = new Card("\ud83c\udca9");
	Card TEN_OF_SPADES   = new Card("\ud83c\udcaa");
	Card JACK_OF_SPADES  = new Card("\ud83c\udcab");
	Card QUEEN_OF_SPADES = new Card("\ud83c\udcad");
	Card KING_OF_SPADES  = new Card("\ud83c\udcae");

	Card ACE_OF_HEARTS   = new Card("\ud83c\udcb1");
	Card TWO_OF_HEARTS   = new Card("\ud83c\udcb2");
	Card THREE_OF_HEARTS = new Card("\ud83c\udcb3");
	Card FOUR_OF_HEARTS  = new Card("\ud83c\udcb4");
	Card FIVE_OF_HEARTS  = new Card("\ud83c\udcb5");
	Card SIX_OF_HEARTS   = new Card("\ud83c\udcb6");
	Card SEVEN_OF_HEARTS = new Card("\ud83c\udcb7");
	Card EIGHT_OF_HEARTS = new Card("\ud83c\udcb8");
	Card NINE_OF_HEARTS  = new Card("\ud83c\udcb9");
	Card TEN_OF_HEARTS   = new Card("\ud83c\udcba");
	Card JACK_OF_HEARTS  = new Card("\ud83c\udcbb");
	Card QUEEN_OF_HEARTS = new Card("\ud83c\udcbd");
	Card KING_OF_HEARTS  = new Card("\ud83c\udcbe");

	Card ACE_OF_DIAMONDS   = new Card("\ud83c\udcc1");
	Card TWO_OF_DIAMONDS   = new Card("\ud83c\udcc2");
	Card THREE_OF_DIAMONDS = new Card("\ud83c\udcc3");
	Card FOUR_OF_DIAMONDS  = new Card("\ud83c\udcc4");
	Card FIVE_OF_DIAMONDS  = new Card("\ud83c\udcc5");
	Card SIX_OF_DIAMONDS   = new Card("\ud83c\udcc6");
	Card SEVEN_OF_DIAMONDS = new Card("\ud83c\udcc7");
	Card EIGHT_OF_DIAMONDS = new Card("\ud83c\udcc8");
	Card NINE_OF_DIAMONDS  = new Card("\ud83c\udcc9");
	Card TEN_OF_DIAMONDS   = new Card("\ud83c\udcca");
	Card JACK_OF_DIAMONDS  = new Card("\ud83c\udccb");
	Card QUEEN_OF_DIAMONDS = new Card("\ud83c\udccd");
	Card KING_OF_DIAMONDS  = new Card("\ud83c\udcce");

	Card ACE_OF_CLUBS   = new Card("\ud83c\udcd1");
	Card TWO_OF_CLUBS   = new Card("\ud83c\udcd2");
	Card THREE_OF_CLUBS = new Card("\ud83c\udcd3");
	Card FOUR_OF_CLUBS  = new Card("\ud83c\udcd4");
	Card FIVE_OF_CLUBS  = new Card("\ud83c\udcd5");
	Card SIX_OF_CLUBS   = new Card("\ud83c\udcd6");
	Card SEVEN_OF_CLUBS = new Card("\ud83c\udcd7");
	Card EIGHT_OF_CLUBS = new Card("\ud83c\udcd8");
	Card NINE_OF_CLUBS  = new Card("\ud83c\udcd9");
	Card TEN_OF_CLUBS   = new Card("\ud83c\udcda");
	Card JACK_OF_CLUBS  = new Card("\ud83c\udcdb");
	Card QUEEN_OF_CLUBS = new Card("\ud83c\udcdd");
	Card KING_OF_CLUBS  = new Card("\ud83c\udcde");

	List<Card> SPADES = Arrays.asList(
			ACE_OF_SPADES,
			TWO_OF_SPADES,
			THREE_OF_SPADES,
			FOUR_OF_SPADES,
			FIVE_OF_SPADES,
			SIX_OF_SPADES,
			SEVEN_OF_SPADES,
			EIGHT_OF_SPADES,
			NINE_OF_SPADES,
			TEN_OF_SPADES,
			JACK_OF_SPADES,
			QUEEN_OF_SPADES,
			KING_OF_SPADES
	);

	List<Card> HEARTS = Arrays.asList(
			ACE_OF_HEARTS,
			TWO_OF_HEARTS,
			THREE_OF_HEARTS,
			FOUR_OF_HEARTS,
			FIVE_OF_HEARTS,
			SIX_OF_HEARTS,
			SEVEN_OF_HEARTS,
			EIGHT_OF_HEARTS,
			NINE_OF_HEARTS,
			TEN_OF_HEARTS,
			JACK_OF_HEARTS,
			QUEEN_OF_HEARTS,
			KING_OF_HEARTS
	);

	List<Card> DIAMONDS = Arrays.asList(
			ACE_OF_DIAMONDS,
			TWO_OF_DIAMONDS,
			THREE_OF_DIAMONDS,
			FOUR_OF_DIAMONDS,
			FIVE_OF_DIAMONDS,
			SIX_OF_DIAMONDS,
			SEVEN_OF_DIAMONDS,
			EIGHT_OF_DIAMONDS,
			NINE_OF_DIAMONDS,
			TEN_OF_DIAMONDS,
			JACK_OF_DIAMONDS,
			QUEEN_OF_DIAMONDS,
			KING_OF_DIAMONDS
	);

	List<Card> CLUBS = Arrays.asList(
			ACE_OF_CLUBS,
			TWO_OF_CLUBS,
			THREE_OF_CLUBS,
			FOUR_OF_CLUBS,
			FIVE_OF_CLUBS,
			SIX_OF_CLUBS,
			SEVEN_OF_CLUBS,
			EIGHT_OF_CLUBS,
			NINE_OF_CLUBS,
			TEN_OF_CLUBS,
			JACK_OF_CLUBS,
			QUEEN_OF_CLUBS,
			KING_OF_CLUBS
	);

	@NotNull
	static List<Card> getNewDeck() {
		return new ArrayList<Card>() {{
			addAll(SPADES);
			addAll(HEARTS);
			addAll(DIAMONDS);
			addAll(CLUBS);
		}};
	}

	Map<String, Card> ALL_CARDS = new HashMap<String, Card>() {{
		getNewDeck().stream()
				.collect(toMap(Card::toString, Function.identity()));
	}};
}
