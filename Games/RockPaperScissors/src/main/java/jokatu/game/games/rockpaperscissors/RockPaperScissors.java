package jokatu.game.games.rockpaperscissors;

import jokatu.game.result.Result;

import static jokatu.game.result.Result.*;

/**
 * An enum that contains entries for rock, paper and scissors.
 */
public enum RockPaperScissors {
	ROCK {
		@Override
		public Result resultAgainst(RockPaperScissors other) {
			switch (other) {
				case ROCK:
					return DRAW;
				case PAPER:
					return LOSE;
				case SCISSORS:
					return WIN;
				default:
					return DRAW;
			}
		}
	},
	PAPER {
		@Override
		public Result resultAgainst(RockPaperScissors other) {
			switch (other) {
				case ROCK:
					return WIN;
				case PAPER:
					return DRAW;
				case SCISSORS:
					return LOSE;
				default:
					return DRAW;
			}
		}
	},
	SCISSORS {
		@Override
		public Result resultAgainst(RockPaperScissors other) {
			switch (other) {
				case ROCK:
					return LOSE;
				case PAPER:
					return WIN;
				case SCISSORS:
					return DRAW;
				default:
					return DRAW;
			}
		}
	};

	public abstract Result resultAgainst(final RockPaperScissors other);
}
