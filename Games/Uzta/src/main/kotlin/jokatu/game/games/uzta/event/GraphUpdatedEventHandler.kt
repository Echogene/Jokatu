package jokatu.game.games.uzta.event

import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import org.springframework.stereotype.Component

@Component
class GraphUpdatedEventHandler : SpecificEventHandler<GraphUpdatedEvent>(GraphUpdatedEvent::class) {
	override fun handleCastEvent(game: Game<*>, event: GraphUpdatedEvent) {
		sender.send("/topic/graph.game." + game.identifier, event.graph)
	}
}
