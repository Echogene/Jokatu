package jokatu.game.cards;

import com.fasterxml.jackson.annotation.JsonValue;

import java.text.MessageFormat;

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
				"{0} of {1}",
				rank.toString().toLowerCase(),
				suit.toString().toLowerCase()
		);
	}

	@JsonValue
	@Override
	public String toString() {
		return display;
	}

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}
}
