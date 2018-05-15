package jokatu.game.joining

import jokatu.game.event.PublicGameEvent
import jokatu.game.player.Player

/**
 * An event for when a player joins a game.
 * @author Steven Weston
 */
class PlayerJoinedEvent(player: Player) : PublicGameEvent {
	override val message = "${player.name} joined the game."
}
