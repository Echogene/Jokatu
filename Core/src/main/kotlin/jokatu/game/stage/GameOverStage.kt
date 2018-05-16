package jokatu.game.stage

import jokatu.game.event.GameEvent
import jokatu.game.input.Input
import jokatu.game.input.UnacceptableInputException
import jokatu.game.player.Player
import jokatu.game.status.StandardTextStatus
import ophelia.collections.BaseCollection
import ophelia.collections.set.EmptySet.emptySet
import ophelia.event.observable.AbstractSynchronousObservable

/**
 * A [Stage] that happens after the game is over.  No more input should be accepted at this point.
 */
class GameOverStage(status: StandardTextStatus) : AbstractSynchronousObservable<GameEvent>(), Stage<GameEvent> {

	init {
		status.text = "Game over."
	}

	override val acceptedInputs: BaseCollection<Class<out Input>>
		get() {
			return emptySet()
		}

	@Throws(Exception::class)
	override fun accept(input: Input, player: Player) {
		throw UnacceptableInputException("The game is over!")
	}
}
