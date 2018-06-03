package jokatu.components.eventhandlers

import jokatu.game.Game
import jokatu.game.event.PublicGameEvent
import jokatu.game.event.SpecificEventHandler
import org.springframework.stereotype.Component

/**
 * Handle public events for a game by sending the event to the game's public channel.
 * @author Steven Weston
 */
@Component
class PublicEventHandler : SpecificEventHandler<PublicGameEvent>(PublicGameEvent::class) {
	public override fun handleCastEvent(game: Game<*>, event: PublicGameEvent) {
		sender.send("/topic/public.game." + game.identifier, event.message)
	}
}
