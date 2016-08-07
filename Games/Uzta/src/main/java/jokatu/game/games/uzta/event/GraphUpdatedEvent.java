package jokatu.game.games.uzta.event;

import jokatu.game.event.GameEvent;
import ophelia.graph.Graph;

public class GraphUpdatedEvent implements GameEvent {
	private final Graph<?, ?> graph;

	public GraphUpdatedEvent(Graph<?, ?> graph) {
		this.graph = graph;
	}

	public Graph<?, ?> getGraph() {
		return graph;
	}
}
