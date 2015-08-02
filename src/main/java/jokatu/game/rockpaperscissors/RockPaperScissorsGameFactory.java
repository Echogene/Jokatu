package jokatu.game.rockpaperscissors;

import jokatu.game.GameID;
import jokatu.game.factory.AbstractGameFactory;
import jokatu.game.factory.Factory;

@Factory
public class RockPaperScissorsGameFactory extends AbstractGameFactory<RockPaperScissorsGame> {

	public static final String ROCK_PAPER_SCISSORS = "Rock/paper/scissors";

	@Override
	protected RockPaperScissorsGame produce(GameID gameID) {
		return new RockPaperScissorsGame(gameID);
	}

	@Override
	public String getGameName() {
		return ROCK_PAPER_SCISSORS;
	}
}
