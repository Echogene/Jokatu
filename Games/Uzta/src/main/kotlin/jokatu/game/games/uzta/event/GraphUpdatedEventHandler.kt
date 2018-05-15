package jokatu.game.games.uzta.event

import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import org.springframework.stereotype.Component

@Component
class GraphUpdatedEventHandler : SpecificEventHandler<GraphUpdatedEvent>() {
	override val eventClass: Class<GraphUpdatedEvent>
		get() = GraphUpdatedEvent::class.java

	override fun handleCastEvent(game: Game<*>, event: GraphUpdatedEvent) {
		sender.send("/topic/graph.game." + game.identifier, event.graph)
	}
}
