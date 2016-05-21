package jokatu.game.cards;

public class Card {

	private final String display;

	public Card(String display) {
		this.display = display;
	}

	@Override
	public String toString() {
		return display;
	}
}
