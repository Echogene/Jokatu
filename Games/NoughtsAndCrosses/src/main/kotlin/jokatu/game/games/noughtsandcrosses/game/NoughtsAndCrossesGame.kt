package jokatu.game.games.noughtsandcrosses.game

import jokatu.game.Game
import jokatu.game.GameID
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer
import jokatu.game.stage.GameOverStage
import jokatu.game.stage.JoiningStage
import jokatu.game.stage.machine.SequentialStageMachine
import jokatu.game.status.StandardTextStatus

class NoughtsAndCrossesGame internal constructor(identifier: GameID) : Game<NoughtsAndCrossesPlayer>(identifier) {

	private val status = StandardTextStatus()

	override val gameName: String
		get() = NOUGHTS_AND_CROSSES

	init {
		stageMachine = SequentialStageMachine(
				{ JoiningStage(NoughtsAndCrossesPlayer::class, players, 2, status) },
				{ AllegianceStage(players.values, status) },
				{ InputStage(players.values, status) },
				{ GameOverStage(status) }
		)

		status.observe({ this.fireEvent(it) })
	}

	companion object {
		const val NOUGHTS_AND_CROSSES = "Noughts and crosses"
	}
}
