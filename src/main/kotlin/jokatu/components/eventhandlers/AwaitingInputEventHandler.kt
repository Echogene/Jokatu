package jokatu.components.eventhandlers

import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import jokatu.game.input.AwaitingInputEvent
import org.springframework.stereotype.Component

@Component
class AwaitingInputEventHandler : SpecificEventHandler<AwaitingInputEvent>() {

	override val eventClass: Class<AwaitingInputEvent>
		get() = AwaitingInputEvent::class.java

	override fun handleCastEvent(game: Game<*>, event: AwaitingInputEvent) {
		game.getPlayers().stream().forEach { player ->
			sender.sendToUser(
					player.name,
					"/topic/awaiting.game." + game.identifier,
					event.awaitingPlayers.contains(player)
			)
		}
	}
}
