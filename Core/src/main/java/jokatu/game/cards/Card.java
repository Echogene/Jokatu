package jokatu.game.cards;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

/**
 * Instances of this class represent one of the 52 standard playingcards.
 */
public class Card {

	private final String text;
	private final String label;
	private final Rank rank;
	private final Suit suit;

	Card(@NotNull String text, @NotNull Rank rank, @NotNull Suit suit) {
		this.text = text;
		this.rank = rank;
		this.suit = suit;

		label = MessageFormat.format(
				"the {0} of {1}",
				rank.toString().toLowerCase(),
				suit.toString().toLowerCase()
		);
	}

	@NotNull
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return text;
	}

	@NotNull
	public Rank getRank() {
		return rank;
	}

	@NotNull
	public Suit getSuit() {
		return suit;
	}

	@NotNull
	public String getLabel() {
		return label;
	}
}
