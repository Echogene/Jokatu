package jokatu.game.cards;

import com.fasterxml.jackson.annotation.JsonValue;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

/**
 * Instances of this class represent one of the 52 standard playingcards.
 */
public class Card {

	private final String display;
	private final String label;
	private final Rank rank;
	private final Suit suit;

	public Card(String display, Rank rank, Suit suit) {
		this.display = display;
		this.rank = rank;
		this.suit = suit;

		label = MessageFormat.format(
				"the {0} of {1}",
				rank.toString().toLowerCase(),
				suit.toString().toLowerCase()
		);
	}

	@JsonValue
	@Override
	public String toString() {
		return display;
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
