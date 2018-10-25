package jokatu.components.eventhandlers

import jokatu.components.stomp.AwaitingInput
import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import jokatu.game.input.AwaitingInputEvent
import org.springframework.stereotype.Component

@Component
class AwaitingInputEventHandler : SpecificEventHandler<AwaitingInputEvent>(AwaitingInputEvent::class) {
	override fun handleCastEvent(game: Game<*>, event: AwaitingInputEvent) {
		game.getPlayers().stream().forEach { player ->
			sender.sendToUser(player.name, AwaitingInput(game), event.awaitingPlayers.contains(player))
		}
	}
}
