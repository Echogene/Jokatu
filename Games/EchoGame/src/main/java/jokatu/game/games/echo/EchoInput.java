package jokatu.game.games.echo;

import jokatu.game.input.Input;

public class EchoInput implements Input {

	private final String string;

	public EchoInput(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}
}
