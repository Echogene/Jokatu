package jokatu.game.cards

/**
 * Instances of this class represent one of the 52 standard playingcards.
 */
// This is converted to JSON using Jackson.
class Card internal constructor(val text: String, val rank: Rank, val suit: Suit) {
	val label: String = "the ${rank.toString().toLowerCase()} of ${suit.toString().toLowerCase()}"

	override fun toString() = text

	fun getId(): String {
		return text
	}
}
