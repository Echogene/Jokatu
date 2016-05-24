package jokatu.game.cards;

public enum Rank {

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

	private final int rank;

	Rank(int rank) {
		this.rank = rank;
	}

	public static Rank getRankOfValue(int rank) {
		return Rank.values()[rank];
	}

	public int getValue() {
		return rank;
	}
}
