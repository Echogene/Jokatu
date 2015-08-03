package jokatu.game.empty;

import jokatu.game.GameID;
import jokatu.game.factory.game.AbstractGameFactory;
import jokatu.game.factory.Factory;

@Factory
public class EmptyGameFactory extends AbstractGameFactory<EmptyGame> {

	@Override
	public String getGameName() {
		return EmptyGame.EMPTY_GAME;
	}

	@Override
	protected EmptyGame produce(GameID gameID) {
		return new EmptyGame(gameID);
	}
}
