package jokatu.game.event

import jokatu.game.Game
import kotlin.reflect.KClass
import kotlin.reflect.full.cast

/**
 * Listens to events but ignores the ones that aren't for the game and the event type it specifies.
 * @param <G> the type of [Game] to accept
 * @param <E> the type of [GameEvent] to accept
 */
abstract class AbstractEventHandler<G : Game<*>, E : GameEvent>(
		private val gameClass: KClass<G>,
		eventClass: KClass<E>
) : SpecificEventHandler<E>(eventClass) {
	override fun handleCastEvent(game: Game<*>, event: E) {
		if (gameClass.isInstance(game)) {
			handleCastGameAndEvent(gameClass.cast(game), event)
		}
	}

	protected abstract fun handleCastGameAndEvent(game: G, event: E)
}
