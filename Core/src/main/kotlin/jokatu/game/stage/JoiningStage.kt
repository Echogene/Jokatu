package jokatu.game.stage

import jokatu.game.event.GameEvent
import jokatu.game.event.StageOverEvent
import jokatu.game.input.Input
import jokatu.game.joining.JoinInputAcceptor
import jokatu.game.joining.PlayerJoinedEvent
import jokatu.game.player.Player
import jokatu.game.status.StandardTextStatus
import ophelia.collections.BaseCollection
import ophelia.event.observable.AbstractSynchronousObservable

/**
 * A [Stage] in which a fixed number of players have to join.  When the limit is reached, the next stage starts
 * automatically.
 * @param <P> the type of player that should be joining the stage
 */
class JoiningStage<P : Player>(
		playerClass: Class<P>,
		private val players: MutableMap<String, P>,
		private val number: Int,
		private val status: StandardTextStatus
) : AbstractSynchronousObservable<GameEvent>(), Stage<GameEvent> {

	private val inputAcceptor: JoinInputAcceptor<P> = JoinInputAcceptor(playerClass, players, number)

	override val acceptedInputs: BaseCollection<Class<out Input>>
		get() = inputAcceptor.acceptedInputs

	init {

		status.setText(
				"Waiting for {0} player{1} to join.",
				number,
				if (number == 1) "" else "s"
		)

		inputAcceptor.observe({ this.onPlayerJoin(it) })

		// Forward the events.
		inputAcceptor.observe({ this.fireEvent(it) })
	}

	private fun onPlayerJoin(gameEvent: PlayerJoinedEvent) {
		if (players.size == number) {
			fireEvent(StageOverEvent())
		} else {
			val more = number - players.size
			status.setText(
					"Waiting for {0} more player{1} to join.",
					more,
					if (more == 1) "" else "s"
			)
		}
	}

	@Throws(Exception::class)
	override fun accept(input: Input, player: Player) {
		inputAcceptor.accept(input, player)
	}
}
