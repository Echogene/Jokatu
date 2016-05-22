package jokatu.game.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StandardCard {

	public static final Card PRIVATE_CARD    = new Card("\ud83c\udca0");

	public static final Card ACE_OF_SPADES   = new Card("\ud83c\udca1");
	public static final Card TWO_OF_SPADES   = new Card("\ud83c\udca2");
	public static final Card THREE_OF_SPADES = new Card("\ud83c\udca3");
	public static final Card FOUR_OF_SPADES  = new Card("\ud83c\udca4");
	public static final Card FIVE_OF_SPADES  = new Card("\ud83c\udca5");
	public static final Card SIX_OF_SPADES   = new Card("\ud83c\udca6");
	public static final Card SEVEN_OF_SPADES = new Card("\ud83c\udca7");
	public static final Card EIGHT_OF_SPADES = new Card("\ud83c\udca8");
	public static final Card NINE_OF_SPADES  = new Card("\ud83c\udca9");
	public static final Card TEN_OF_SPADES   = new Card("\ud83c\udcaa");
	public static final Card JACK_OF_SPADES  = new Card("\ud83c\udcab");
	public static final Card QUEEN_OF_SPADES = new Card("\ud83c\udcad");
	public static final Card KING_OF_SPADES  = new Card("\ud83c\udcae");

	public static final Card ACE_OF_HEARTS   = new Card("\ud83c\udcb1");
	public static final Card TWO_OF_HEARTS   = new Card("\ud83c\udcb2");
	public static final Card THREE_OF_HEARTS = new Card("\ud83c\udcb3");
	public static final Card FOUR_OF_HEARTS  = new Card("\ud83c\udcb4");
	public static final Card FIVE_OF_HEARTS  = new Card("\ud83c\udcb5");
	public static final Card SIX_OF_HEARTS   = new Card("\ud83c\udcb6");
	public static final Card SEVEN_OF_HEARTS = new Card("\ud83c\udcb7");
	public static final Card EIGHT_OF_HEARTS = new Card("\ud83c\udcb8");
	public static final Card NINE_OF_HEARTS  = new Card("\ud83c\udcb9");
	public static final Card TEN_OF_HEARTS   = new Card("\ud83c\udcba");
	public static final Card JACK_OF_HEARTS  = new Card("\ud83c\udcbb");
	public static final Card QUEEN_OF_HEARTS = new Card("\ud83c\udcbd");
	public static final Card KING_OF_HEARTS  = new Card("\ud83c\udcbe");

	public static final Card ACE_OF_DIAMONDS   = new Card("\ud83c\udcc1");
	public static final Card TWO_OF_DIAMONDS   = new Card("\ud83c\udcc2");
	public static final Card THREE_OF_DIAMONDS = new Card("\ud83c\udcc3");
	public static final Card FOUR_OF_DIAMONDS  = new Card("\ud83c\udcc4");
	public static final Card FIVE_OF_DIAMONDS  = new Card("\ud83c\udcc5");
	public static final Card SIX_OF_DIAMONDS   = new Card("\ud83c\udcc6");
	public static final Card SEVEN_OF_DIAMONDS = new Card("\ud83c\udcc7");
	public static final Card EIGHT_OF_DIAMONDS = new Card("\ud83c\udcc8");
	public static final Card NINE_OF_DIAMONDS  = new Card("\ud83c\udcc9");
	public static final Card TEN_OF_DIAMONDS   = new Card("\ud83c\udcca");
	public static final Card JACK_OF_DIAMONDS  = new Card("\ud83c\udccb");
	public static final Card QUEEN_OF_DIAMONDS = new Card("\ud83c\udccd");
	public static final Card KING_OF_DIAMONDS  = new Card("\ud83c\udcce");

	public static final Card ACE_OF_CLUBS   = new Card("\ud83c\udcd1");
	public static final Card TWO_OF_CLUBS   = new Card("\ud83c\udcd2");
	public static final Card THREE_OF_CLUBS = new Card("\ud83c\udcd3");
	public static final Card FOUR_OF_CLUBS  = new Card("\ud83c\udcd4");
	public static final Card FIVE_OF_CLUBS  = new Card("\ud83c\udcd5");
	public static final Card SIX_OF_CLUBS   = new Card("\ud83c\udcd6");
	public static final Card SEVEN_OF_CLUBS = new Card("\ud83c\udcd7");
	public static final Card EIGHT_OF_CLUBS = new Card("\ud83c\udcd8");
	public static final Card NINE_OF_CLUBS  = new Card("\ud83c\udcd9");
	public static final Card TEN_OF_CLUBS   = new Card("\ud83c\udcda");
	public static final Card JACK_OF_CLUBS  = new Card("\ud83c\udcdb");
	public static final Card QUEEN_OF_CLUBS = new Card("\ud83c\udcdd");
	public static final Card KING_OF_CLUBS  = new Card("\ud83c\udcde");

	public static final List<Card> SPADES = Arrays.asList(
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

	public static final List<Card> HEARTS = Arrays.asList(
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

	public static final List<Card> DIAMONDS = Arrays.asList(
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

	public static final List<Card> CLUBS = Arrays.asList(
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

	public static final List<Card> ALL_CARDS = new ArrayList<Card>() {{
		addAll(SPADES);
		addAll(HEARTS);
		addAll(DIAMONDS);
		addAll(CLUBS);
	}};
}
