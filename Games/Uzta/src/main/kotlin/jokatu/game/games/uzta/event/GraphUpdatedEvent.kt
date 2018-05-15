package jokatu.game.games.uzta.event

import jokatu.game.event.GameEvent
import ophelia.graph.Graph

/**
 * An event fired when a graph is updated.
 */
class GraphUpdatedEvent(val graph: Graph<*, *>) : GameEvent
