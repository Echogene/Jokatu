package jokatu.game.games.gameofgames.game;

import jokatu.game.Stage;
import jokatu.game.games.gameofgames.input.CreateGameInputAcceptor;

class GameOfGameStage extends Stage {
	GameOfGameStage() {
		super(new CreateGameInputAcceptor());
	}
}
