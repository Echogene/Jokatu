package jokatu.game.games.echo.game;

import jokatu.game.Stage;
import jokatu.game.games.echo.input.EchoInputAcceptor;

class EchoStage extends Stage {

	EchoStage() {
		super(new EchoInputAcceptor());
	}
}
