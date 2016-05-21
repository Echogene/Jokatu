package jokatu.game.games.cards.input;

import jokatu.game.input.Input;

public class CardInput implements Input {

	private final String string;

	public CardInput(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}
}
