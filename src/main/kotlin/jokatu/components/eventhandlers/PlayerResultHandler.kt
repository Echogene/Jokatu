package jokatu.components.eventhandlers

import jokatu.components.stomp.GameResult
import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import jokatu.game.result.PlayerResult
import org.springframework.stereotype.Component

/**
 * At the end of a game, send the message of the [PlayerResult] to a special STOMP destination.
 */
@Component
class PlayerResultHandler : SpecificEventHandler<PlayerResult>(PlayerResult::class) {
	override fun handleCastEvent(game: Game<*>, event: PlayerResult) {
		sender.send(GameResult(game), event.message)
	}
}
