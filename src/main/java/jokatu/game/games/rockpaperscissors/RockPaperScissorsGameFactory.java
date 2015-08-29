package jokatu.game.games.rockpaperscissors;

import jokatu.game.GameID;
import jokatu.game.factory.Factory;
import jokatu.game.factory.game.AbstractGameFactory;
import org.jetbrains.annotations.NotNull;

import static jokatu.game.games.rockpaperscissors.RockPaperScissorsGame.ROCK_PAPER_SCISSORS;

@Factory(gameName = ROCK_PAPER_SCISSORS)
public class RockPaperScissorsGameFactory extends AbstractGameFactory {

	@NotNull
	@Override
	protected RockPaperScissorsGame produce(GameID gameID) {
		return new RockPaperScissorsGame(gameID);
	}
}
