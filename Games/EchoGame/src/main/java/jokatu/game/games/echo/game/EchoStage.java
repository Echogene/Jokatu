package jokatu.game.games.echo.game;

import jokatu.game.MultiInputStage;
import jokatu.game.games.echo.input.EchoInputAcceptor;

class EchoStage extends MultiInputStage {

	EchoStage() {
		super(new EchoInputAcceptor());
	}
}
