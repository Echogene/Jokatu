package jokatu.game.games.rockpaperscissors;

import jokatu.game.result.Result;
import org.jetbrains.annotations.NotNull;

import static jokatu.game.result.Result.*;

/**
 * An enum that contains entries for rock, paper and scissors.
 */
public enum RockPaperScissors {
	ROCK {
		@Override
		public Result resultAgainst(RockPaperScissors other) {
			return result(other, DRAW, LOSE, WIN);
		}
	},
	PAPER {
		@Override
		public Result resultAgainst(RockPaperScissors other) {
			return result(other, WIN, DRAW, LOSE);
		}
	},
	SCISSORS {
		@Override
		public Result resultAgainst(RockPaperScissors other) {
			return result(other, LOSE, WIN, DRAW);
		}
	};

	@NotNull
	private static Result result(RockPaperScissors other, Result againstRock, Result againstPaper, Result againstScissors) {
		switch (other) {
			case ROCK:
				return againstRock;
			case PAPER:
				return againstPaper;
			case SCISSORS:
				return againstScissors;
			default:
				return DRAW;
		}
	}

	public abstract Result resultAgainst(final RockPaperScissors other);
}
