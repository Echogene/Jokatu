package jokatu.game.cards

import jokatu.game.cards.Rank.*
import jokatu.game.cards.Suit.*
import java.util.*
import java.util.stream.Collectors.groupingBy
import java.util.stream.Collectors.toMap

/**
 * Contains constants for all of the 52 standard playingcards.
 */
object Cards {

	const val PRIVATE_CARD = "\ud83c\udca0"

	private val ALL_CARDS = ArrayList<Card>(52)

	val ACE_OF_SPADES = createCard("\ud83c\udca1", ACE, SPADES)
	val TWO_OF_SPADES = createCard("\ud83c\udca2", TWO, SPADES)
	val THREE_OF_SPADES = createCard("\ud83c\udca3", THREE, SPADES)
	val FOUR_OF_SPADES = createCard("\ud83c\udca4", FOUR, SPADES)
	val FIVE_OF_SPADES = createCard("\ud83c\udca5", FIVE, SPADES)
	val SIX_OF_SPADES = createCard("\ud83c\udca6", SIX, SPADES)
	val SEVEN_OF_SPADES = createCard("\ud83c\udca7", SEVEN, SPADES)
	val EIGHT_OF_SPADES = createCard("\ud83c\udca8", EIGHT, SPADES)
	val NINE_OF_SPADES = createCard("\ud83c\udca9", NINE, SPADES)
	val TEN_OF_SPADES = createCard("\ud83c\udcaa", TEN, SPADES)
	val JACK_OF_SPADES = createCard("\ud83c\udcab", JACK, SPADES)
	val QUEEN_OF_SPADES = createCard("\ud83c\udcad", QUEEN, SPADES)
	val KING_OF_SPADES = createCard("\ud83c\udcae", KING, SPADES)

	val ACE_OF_HEARTS = createCard("\ud83c\udcb1", ACE, HEARTS)
	val TWO_OF_HEARTS = createCard("\ud83c\udcb2", TWO, HEARTS)
	val THREE_OF_HEARTS = createCard("\ud83c\udcb3", THREE, HEARTS)
	val FOUR_OF_HEARTS = createCard("\ud83c\udcb4", FOUR, HEARTS)
	val FIVE_OF_HEARTS = createCard("\ud83c\udcb5", FIVE, HEARTS)
	val SIX_OF_HEARTS = createCard("\ud83c\udcb6", SIX, HEARTS)
	val SEVEN_OF_HEARTS = createCard("\ud83c\udcb7", SEVEN, HEARTS)
	val EIGHT_OF_HEARTS = createCard("\ud83c\udcb8", EIGHT, HEARTS)
	val NINE_OF_HEARTS = createCard("\ud83c\udcb9", NINE, HEARTS)
	val TEN_OF_HEARTS = createCard("\ud83c\udcba", TEN, HEARTS)
	val JACK_OF_HEARTS = createCard("\ud83c\udcbb", JACK, HEARTS)
	val QUEEN_OF_HEARTS = createCard("\ud83c\udcbd", QUEEN, HEARTS)
	val KING_OF_HEARTS = createCard("\ud83c\udcbe", KING, HEARTS)

	val ACE_OF_DIAMONDS = createCard("\ud83c\udcc1", ACE, DIAMONDS)
	val TWO_OF_DIAMONDS = createCard("\ud83c\udcc2", TWO, DIAMONDS)
	val THREE_OF_DIAMONDS = createCard("\ud83c\udcc3", THREE, DIAMONDS)
	val FOUR_OF_DIAMONDS = createCard("\ud83c\udcc4", FOUR, DIAMONDS)
	val FIVE_OF_DIAMONDS = createCard("\ud83c\udcc5", FIVE, DIAMONDS)
	val SIX_OF_DIAMONDS = createCard("\ud83c\udcc6", SIX, DIAMONDS)
	val SEVEN_OF_DIAMONDS = createCard("\ud83c\udcc7", SEVEN, DIAMONDS)
	val EIGHT_OF_DIAMONDS = createCard("\ud83c\udcc8", EIGHT, DIAMONDS)
	val NINE_OF_DIAMONDS = createCard("\ud83c\udcc9", NINE, DIAMONDS)
	val TEN_OF_DIAMONDS = createCard("\ud83c\udcca", TEN, DIAMONDS)
	val JACK_OF_DIAMONDS = createCard("\ud83c\udccb", JACK, DIAMONDS)
	val QUEEN_OF_DIAMONDS = createCard("\ud83c\udccd", QUEEN, DIAMONDS)
	val KING_OF_DIAMONDS = createCard("\ud83c\udcce", KING, DIAMONDS)

	val ACE_OF_CLUBS = createCard("\ud83c\udcd1", ACE, CLUBS)
	val TWO_OF_CLUBS = createCard("\ud83c\udcd2", TWO, CLUBS)
	val THREE_OF_CLUBS = createCard("\ud83c\udcd3", THREE, CLUBS)
	val FOUR_OF_CLUBS = createCard("\ud83c\udcd4", FOUR, CLUBS)
	val FIVE_OF_CLUBS = createCard("\ud83c\udcd5", FIVE, CLUBS)
	val SIX_OF_CLUBS = createCard("\ud83c\udcd6", SIX, CLUBS)
	val SEVEN_OF_CLUBS = createCard("\ud83c\udcd7", SEVEN, CLUBS)
	val EIGHT_OF_CLUBS = createCard("\ud83c\udcd8", EIGHT, CLUBS)
	val NINE_OF_CLUBS = createCard("\ud83c\udcd9", NINE, CLUBS)
	val TEN_OF_CLUBS = createCard("\ud83c\udcda", TEN, CLUBS)
	val JACK_OF_CLUBS = createCard("\ud83c\udcdb", JACK, CLUBS)
	val QUEEN_OF_CLUBS = createCard("\ud83c\udcdd", QUEEN, CLUBS)
	val KING_OF_CLUBS = createCard("\ud83c\udcde", KING, CLUBS)

	val CARDS_BY_DISPLAY: Map<String, Card> = ALL_CARDS.stream()
			.collect(toMap({ it.toString() }, { c: Card -> c }))

	val CARDS_BY_SUIT_AND_RANK: Map<Suit, Map<Rank, Card>> = ALL_CARDS.stream()
			.collect(groupingBy({ it.suit }, toMap({ it.rank }, { c: Card -> c })))

	val CARDS_BY_RANK_AND_SUIT: Map<Rank, Map<Suit, Card>> = ALL_CARDS.stream()
			.collect(groupingBy({ it.rank }, toMap({ it.suit }, { c: Card -> c })))

	val newDeck: List<Card>
		get() = ArrayList(ALL_CARDS)

	private fun createCard(display: String, rank: Rank, suit: Suit): Card {
		val card = Card(display, rank, suit)
		ALL_CARDS.add(card)
		return card
	}
}
