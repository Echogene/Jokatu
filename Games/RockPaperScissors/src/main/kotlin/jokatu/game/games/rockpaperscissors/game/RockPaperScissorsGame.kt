package jokatu.game.games.rockpaperscissors.game

import jokatu.game.Game
import jokatu.game.GameID
import jokatu.game.player.StandardPlayer
import jokatu.game.stage.GameOverStage
import jokatu.game.stage.JoiningStage
import jokatu.game.stage.machine.SequentialStageMachine
import jokatu.game.status.StandardTextStatus

class RockPaperScissorsGame internal constructor(identifier: GameID) : Game<StandardPlayer>(identifier) {

	private val status = StandardTextStatus()

	override val gameName: String
		get() = ROCK_PAPER_SCISSORS

	init {

		stageMachine = SequentialStageMachine(
				{ JoiningStage(StandardPlayer::class.java, players, 2, status) },
				{ InputStage(players.values, status) },
				{ GameOverStage(status) }
		)

		status.observe({ this.fireEvent(it) })
	}

	companion object {
		const val ROCK_PAPER_SCISSORS = "Rock/paper/scissors"
	}
}
