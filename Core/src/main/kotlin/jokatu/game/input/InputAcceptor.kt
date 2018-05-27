package jokatu.game.input

import jokatu.game.Game
import jokatu.game.event.GameEvent
import jokatu.game.player.Player
import ophelia.event.observable.Observable
import kotlin.reflect.KClass

/**
 * Accepts an [Input] from a [Player], handles it, and may fire [GameEvent]s.  These are to be
 * created in the context of a single [Game] and will most likely have access to various parts of the game's
 * state.
 *
 * @param <E> the type of [GameEvent] that this may fire
 */
interface InputAcceptor<E : GameEvent> : Observable<E> {

	val acceptedInputs: Collection<KClass<out Input>>

	@Throws(Exception::class)
	fun accept(input: Input, player: Player)
}
