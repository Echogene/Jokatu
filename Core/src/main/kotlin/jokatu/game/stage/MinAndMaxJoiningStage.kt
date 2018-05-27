package jokatu.game.stage

import jokatu.game.MultiInputStage
import jokatu.game.event.StageOverEvent
import jokatu.game.input.finishstage.EndStageInput
import jokatu.game.joining.JoinInputAcceptor
import jokatu.game.joining.PlayerJoinedEvent
import jokatu.game.joining.finish.FinishJoiningInputAcceptor
import jokatu.game.player.Player
import jokatu.game.status.StandardTextStatus
import kotlin.reflect.KClass

/**
 * A [Stage] in which a maximum and minimum number of players can join.  When there are enough players, it
 * can accept a [EndStageInput] to start the next stage. When the maximum is reached, the next stage starts
 * automatically.
 * @param <P> the type of player that should be joining the stage
 */
class MinAndMaxJoiningStage<P : Player>(
		playerClass: KClass<P>,
		private val players: MutableMap<String, P>,
		private val minimum: Int,
		private val maximum: Int,
		private val status: StandardTextStatus
) : MultiInputStage() {

	init {
		status.text = "Waiting for at least $minimum player${if (minimum == 1) "" else "s"} to join."

		val joinInputAcceptor = JoinInputAcceptor(playerClass, players, maximum)
		joinInputAcceptor.observe(::onPlayerJoin)
		addInputAcceptor(joinInputAcceptor)

		val finishJoiningInputAcceptor = FinishJoiningInputAcceptor(playerClass, players, minimum)
		addInputAcceptor(finishJoiningInputAcceptor)
	}

	private fun onPlayerJoin(gameEvent: PlayerJoinedEvent) {
		if (players.size == maximum) {
			fireEvent(StageOverEvent())
		} else {
			var more = minimum - players.size
			if (more > 0) {
				status.text = "Waiting for $more more player${if (more == 1) "" else "s"} to join."
			} else {
				more = maximum - players.size
				status.text = "Waiting for someone to start the game or up to $more more " +
						"player${if (more == 1) "" else "s"} to join."

			}
		}
	}
}
