package jokatu.game.cards

/**
 * The rank of a standard playingcard is one of the 13 values it can take from ace to king.
 */
enum class Rank(val value: Int) {
	ACE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5),
	SIX(6),
	SEVEN(7),
	EIGHT(8),
	NINE(9),
	TEN(10),
	JACK(11),
	QUEEN(12),
	KING(13);
}
