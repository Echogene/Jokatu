package jokatu.game.rockpaperscissors;

import jokatu.game.GameID;
import jokatu.game.factory.AbstractGameFactory;
import jokatu.game.factory.Factory;

@Factory
public class RockPaperScissorsFactory extends AbstractGameFactory<RockPaperScissorsGame> {
	@Override
	protected RockPaperScissorsGame produce(GameID gameID) {
		return new RockPaperScissorsGame(gameID);
	}

	@Override
	public String getGameName() {
		return "Rock/paper/scissors";
	}
}
