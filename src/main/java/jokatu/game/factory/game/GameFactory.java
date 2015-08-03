package jokatu.game.factory.game;

import jokatu.game.Game;

public interface GameFactory<G extends Game<?, ?, ?, ?>> {

	G produce();
}
