package jokatu.game.games.gameofgames.input;

import jokatu.game.input.Input;

public class CreateGameInput implements Input {

	private final String gameName;

	CreateGameInput(String gameName) {
		this.gameName = gameName;
	}

	public String getGameName() {
		return gameName;
	}
}
