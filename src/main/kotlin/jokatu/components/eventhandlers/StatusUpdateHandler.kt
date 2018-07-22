package jokatu.components.eventhandlers

import jokatu.components.stomp.GameStatus
import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import jokatu.game.event.StatusUpdateEvent
import org.springframework.stereotype.Component

/**
 * Handle status updates for a game by sending the new status to the game's status channel.
 * @author Steven Weston
 */
@Component
class StatusUpdateHandler : SpecificEventHandler<StatusUpdateEvent>(StatusUpdateEvent::class) {
	override fun handleCastEvent(game: Game<*>, event: StatusUpdateEvent) {
		val text = event.status.text
		if (text != null) {
			sender.send(GameStatus(game), text)
		}
	}
}
