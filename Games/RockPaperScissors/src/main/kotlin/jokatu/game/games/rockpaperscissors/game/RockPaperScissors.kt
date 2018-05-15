package jokatu.game.games.rockpaperscissors.game

import jokatu.game.result.Result

import jokatu.game.result.Result.*

/**
 * An enum that contains entries for rock, paper and scissors.
 */
enum class RockPaperScissors {
	ROCK {
		override fun resultAgainst(other: RockPaperScissors): Result {
			return result(other, DRAW, LOSE, WIN)
		}
	},
	PAPER {
		override fun resultAgainst(other: RockPaperScissors): Result {
			return result(other, WIN, DRAW, LOSE)
		}
	},
	SCISSORS {
		override fun resultAgainst(other: RockPaperScissors): Result {
			return result(other, LOSE, WIN, DRAW)
		}
	};

	fun result(other: RockPaperScissors, againstRock: Result, againstPaper: Result, againstScissors: Result): Result {
		return when (other) {
			ROCK -> againstRock
			PAPER -> againstPaper
			SCISSORS -> againstScissors
		}
	}

	abstract fun resultAgainst(other: RockPaperScissors): Result
}
