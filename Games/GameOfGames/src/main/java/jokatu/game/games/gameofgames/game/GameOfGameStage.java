package jokatu.game.games.gameofgames.game;

import jokatu.game.MultiInputStage;
import jokatu.game.games.gameofgames.input.CreateGameInputAcceptor;

class GameOfGameStage extends MultiInputStage {
	GameOfGameStage() {
		super(new CreateGameInputAcceptor());
	}
}
