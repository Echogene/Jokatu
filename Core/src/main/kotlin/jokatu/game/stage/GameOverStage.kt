package jokatu.game.stage

import jokatu.game.event.GameEvent
import jokatu.game.input.Input
import jokatu.game.input.UnacceptableInputException
import jokatu.game.player.Player
import jokatu.game.status.StandardTextStatus
import ophelia.event.observable.AbstractSynchronousObservable
import kotlin.reflect.KClass

/**
 * A [Stage] that happens after the game is over.  No more input should be accepted at this point.
 */
class GameOverStage(status: StandardTextStatus) : AbstractSynchronousObservable<GameEvent>(), Stage<GameEvent> {

	init {
		status.text = "Game over."
	}

	override val acceptedInputs: Collection<KClass<out Input>>
		get() = setOf()

	@Throws(Exception::class)
	override fun accept(input: Input, player: Player) {
		throw UnacceptableInputException("The game is over!")
	}
}
