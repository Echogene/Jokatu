package jokatu.game.empty;

import jokatu.game.GameID;
import jokatu.game.factory.AbstractGameFactory;
import jokatu.game.factory.Factory;

@Factory
public class EmptyGameFactory extends AbstractGameFactory<EmptyGame> {

	public static final String EMPTY_GAME = "Empty game";

	@Override
	public String getGameName() {
		return EMPTY_GAME;
	}

	@Override
	protected EmptyGame produce(GameID gameID) {
		return new EmptyGame(gameID);
	}
}
