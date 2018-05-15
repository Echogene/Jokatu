package jokatu.game.turn

import jokatu.game.event.PublicGameEvent
import jokatu.game.input.AwaitingInputEvent
import jokatu.game.player.Player

import java.text.MessageFormat.format

/**
 * Fired when play is passed from one player to another.
 */
class TurnChangedEvent internal constructor(private val oldPlayer: Player?, val newPlayer: Player) : AwaitingInputEvent(newPlayer), PublicGameEvent {

	override val message: String
		get() = when {
			oldPlayer == null -> format("It''s {0}''s turn.", newPlayer)
			oldPlayer === newPlayer -> format("It''s {0}''s turn again.", oldPlayer)
			else -> format("{0}''s turn is over.  It''s now {1}''s turn.", oldPlayer, newPlayer)
		}
}
