package jokatu.game.cards;

import com.fasterxml.jackson.annotation.JsonValue;

public class Card {

	private final String display;

	public Card(String display) {
		this.display = display;
	}

	@JsonValue
	@Override
	public String toString() {
		return display;
	}
}
