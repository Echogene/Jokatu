package jokatu.game.input

import jokatu.game.event.GameEvent
import jokatu.game.player.Player
import ophelia.collections.set.Singleton
import ophelia.event.observable.AbstractSynchronousObservable
import org.slf4j.LoggerFactory

/**
 * An abstract version of [InputAcceptor] that only accepts a specific type of input from a specific type of
 * player.  It does nothing with the input it is not the type it specifies.
 * @param <I> the type of the [Input] to accept
 * @param <P> the type of the [Player] to accept
 * @param <E> the type of [GameEvent] this can fire
 */
abstract class AbstractInputAcceptor<I : Input, P : Player, E : GameEvent> : AbstractSynchronousObservable<E>(), InputAcceptor<E> {

	private val log = LoggerFactory.getLogger(javaClass)

	protected abstract val inputClass: Class<I>

	protected abstract val playerClass: Class<P>

	override val acceptedInputs: Singleton<Class<out Input>>
		get() = Singleton(inputClass)

	private fun castInput(input: Input): I {
		return inputClass.cast(input)
	}

	private fun castPlayer(player: Player): P {
		return playerClass.cast(player)
	}

	@Throws(Exception::class)
	override fun accept(input: Input, player: Player) {
		val playerClass = playerClass
		if (!playerClass.isInstance(player)) {
			log.debug(
					"Ignoring player {} because it was not a {}",
					input,
					playerClass.simpleName
			)
			return
		}
		val inputClass = inputClass
		if (!inputClass.isInstance(input)) {
			log.debug(
					"Ignoring input {} because it was not a {}",
					input,
					inputClass.simpleName
			)
			return
		}
		acceptCastInputAndPlayer(castInput(input), castPlayer(player))
	}

	@Throws(Exception::class)
	protected abstract fun acceptCastInputAndPlayer(input: I, inputter: P)
}
