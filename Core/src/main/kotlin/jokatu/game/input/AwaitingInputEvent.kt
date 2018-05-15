package jokatu.game.input

import jokatu.game.event.GameEvent
import jokatu.game.player.Player

/**
 * Fire one of these from a game to signify that a collection of players needs to provide input.  Firing one will
 * override any previously fired ones, so the given collection should be considered as the definitive source of players
 * that need to input.
 */
open class AwaitingInputEvent(
		/**
		 * @return the players from whom we're awaiting input.
		 */
		val awaitingPlayers: Collection<Player>
) : GameEvent {
	constructor(player: Player) : this(setOf<Player>(player))
}
