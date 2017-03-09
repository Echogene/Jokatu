package jokatu.game.games.uzta.event;

import jokatu.game.event.GameEvent;
import ophelia.graph.Graph;

/**
 * An event fired when a graph is updated.
 */
public class GraphUpdatedEvent implements GameEvent {
	private final Graph<?, ?> graph;

	public GraphUpdatedEvent(Graph<?, ?> graph) {
		this.graph = graph;
	}

	public Graph<?, ?> getGraph() {
		return graph;
	}
}
