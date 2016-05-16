package jokatu.game.games.gameofgames.input;

import jokatu.game.input.Input;

class CreateGameInput implements Input {

	private final String gameName;

	CreateGameInput(String gameName) {
		this.gameName = gameName;
	}

	String getGameName() {
		return gameName;
	}
}
