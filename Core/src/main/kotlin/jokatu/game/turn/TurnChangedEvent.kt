package jokatu.game.turn

import jokatu.game.event.PublicGameEvent
import jokatu.game.input.AwaitingInputEvent
import jokatu.game.player.Player

/**
 * Fired when play is passed from one player to another.
 */
class TurnChangedEvent internal constructor(private val oldPlayer: Player?, val newPlayer: Player) : AwaitingInputEvent(newPlayer), PublicGameEvent {

	override val message: String
		get() = when {
			oldPlayer == null -> "It's $newPlayer's turn."
			oldPlayer === newPlayer -> "It's $oldPlayer's turn again."
			else -> "$oldPlayer's turn is over.  It''s now $newPlayer's turn."
		}
}
