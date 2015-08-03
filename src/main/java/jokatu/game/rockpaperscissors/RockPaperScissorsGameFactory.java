package jokatu.game.rockpaperscissors;

import jokatu.game.GameID;
import jokatu.game.factory.game.AbstractGameFactory;
import jokatu.game.factory.Factory;
import org.jetbrains.annotations.NotNull;

import static jokatu.game.rockpaperscissors.RockPaperScissorsGame.ROCK_PAPER_SCISSORS;

@Factory(gameName = ROCK_PAPER_SCISSORS)
public class RockPaperScissorsGameFactory extends AbstractGameFactory<RockPaperScissorsGame> {

	@NotNull
	@Override
	protected RockPaperScissorsGame produce(GameID gameID) {
		return new RockPaperScissorsGame(gameID);
	}
}
