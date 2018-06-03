package jokatu.game.event

import jokatu.components.stomp.StoringMessageSender
import jokatu.game.Game
import org.springframework.beans.factory.annotation.Autowired
import kotlin.reflect.KClass
import kotlin.reflect.full.cast

/**
 * Listens to events but ignores the ones that aren't for the event type it specifies.
 * @param <E> the type of [GameEvent] to accept
 */
abstract class SpecificEventHandler<E : GameEvent>(
		/**
		 * @return the type of [GameEvent], which would get type-erased, that this [AbstractEventHandler] handles
		 */
		private val eventClass: KClass<E>
) : EventHandler {
	@Autowired
	protected lateinit var sender: StoringMessageSender

	override fun handle(game: Game<*>, e: GameEvent) {
		if (eventClass.isInstance(e)) {
			handleCastEvent(game, eventClass.cast(e))
		}
	}

	protected abstract fun handleCastEvent(game: Game<*>, event: E)
}
