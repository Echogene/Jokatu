package jokatu.game.factory;

import jokatu.game.Game;

public interface GameFactory<G extends Game<?, ?, ?, ?>> {

	String getGameName();

	G produce();
}
