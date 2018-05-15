package jokatu.components.eventhandlers

import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import jokatu.game.result.PlayerResult
import org.springframework.stereotype.Component

/**
 * At the end of a game, send the message of the [PlayerResult] to a special STOMP destination.
 */
@Component
class PlayerResultHandler : SpecificEventHandler<PlayerResult>() {
	override val eventClass: Class<PlayerResult>
		get() = PlayerResult::class.java

	override fun handleCastEvent(game: Game<*>, event: PlayerResult) {
		sender.send("/topic/result.game." + game.identifier, event.message)
	}
}
