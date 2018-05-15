package jokatu.components.eventhandlers

import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import jokatu.game.event.StatusUpdateEvent
import org.springframework.stereotype.Component

/**
 * Handle status updates for a game by sending the new status to the game's status channel.
 * @author Steven Weston
 */
@Component
class StatusUpdateHandler : SpecificEventHandler<StatusUpdateEvent>() {

	override val eventClass: Class<StatusUpdateEvent>
		get() = StatusUpdateEvent::class.java

	override fun handleCastEvent(game: Game<*>, event: StatusUpdateEvent) {
		val text = event.status.text
		if (text != null) {
			sender.send("/topic/status.game." + game.identifier, text)
		}
	}
}
