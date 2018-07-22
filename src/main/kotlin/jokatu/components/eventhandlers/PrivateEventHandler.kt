package jokatu.components.eventhandlers

import jokatu.components.stomp.PrivateMessage
import jokatu.game.Game
import jokatu.game.event.PrivateGameEvent
import jokatu.game.event.SpecificEventHandler
import org.springframework.stereotype.Component

/**
 * Private events should be forwarded to the users they specify.
 * @author Steven Weston
 */
@Component
class PrivateEventHandler : SpecificEventHandler<PrivateGameEvent>(PrivateGameEvent::class) {
	override fun handleCastEvent(game: Game<*>, event: PrivateGameEvent) {
		event.players.stream().forEach { player ->
			sender.sendToUser(player.name, PrivateMessage(game), event.message)
		}
	}
}
