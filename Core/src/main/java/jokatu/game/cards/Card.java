package jokatu.game.cards;

import com.fasterxml.jackson.annotation.JsonValue;

public class Card {

	private final String display;
	private final Rank rank;
	private final Suit suit;

	public Card(String display, Rank rank, Suit suit) {
		this.display = display;
		this.rank = rank;
		this.suit = suit;
	}

	@JsonValue
	@Override
	public String toString() {
		return display;
	}
}
