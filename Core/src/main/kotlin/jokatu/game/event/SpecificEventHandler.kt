package jokatu.game.event

import jokatu.components.stomp.StoringMessageSender
import jokatu.game.Game
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Listens to events but ignores the ones that aren't for the event type it specifies.
 * @param <E> the type of [GameEvent] to accept
 */
@Component
abstract class SpecificEventHandler<E : GameEvent> : EventHandler {
	@Autowired
	protected lateinit var sender: StoringMessageSender

	/**
	 * @return the type of [GameEvent], which would get type-erased, that this [AbstractEventHandler] handles
	 */
	protected abstract val eventClass: Class<E>

	override fun handle(game: Game<*>, e: GameEvent) {
		if (eventClass.isInstance(e)) {
			handleCastEvent(game, eventClass.cast(e))
		}
	}

	protected abstract fun handleCastEvent(game: Game<*>, event: E)
}
