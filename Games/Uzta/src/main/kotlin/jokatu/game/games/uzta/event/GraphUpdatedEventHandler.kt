package jokatu.game.games.uzta.event

import jokatu.components.stomp.Topic
import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import ophelia.kotlin.graph.Graph
import org.springframework.stereotype.Component

@Component
class GraphUpdatedEventHandler : SpecificEventHandler<GraphUpdatedEvent>(GraphUpdatedEvent::class) {
	override fun handleCastEvent(game: Game<*>, event: GraphUpdatedEvent) {
		sender.send(GameGraph(game), event.graph)
	}
}

private class GameGraph(game: Game<*>): Topic<Graph<*, *>>("graph.game.${game.identifier}")