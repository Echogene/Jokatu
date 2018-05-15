package jokatu.game.event

import jokatu.game.Game
import org.springframework.stereotype.Component

/**
 * Listens to events but ignores the ones that aren't for the game and the event type it specifies.
 * @param <G> the type of [Game] to accept
 * @param <E> the type of [GameEvent] to accept
 */
@Component
abstract class AbstractEventHandler<G : Game<*>, E : GameEvent> : SpecificEventHandler<E>() {

	protected abstract val gameClass: Class<G>

	override fun handleCastEvent(game: Game<*>, event: E) {
		if (gameClass.isInstance(game)) {
			handleCastGameAndEvent(gameClass.cast(game), event)
		}
	}

	protected abstract fun handleCastGameAndEvent(game: G, event: E)
}
