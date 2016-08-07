package jokatu.game.games.uzta.graph;

import ophelia.graph.BiGraph;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static java.util.Optional.empty;

public interface UztaGraph extends BiGraph<Node, LineSegment> {

	@NotNull
	default Optional<Node> getNode(@NotNull String id) {
		return getNodes().stream()
				.filter(node -> id.equals(node.getId()))
				.findAny();
	}

	@NotNull
	default Optional<LineSegment> getEdge(@NotNull Node start, @NotNull Node end) {
		if (start.equals(end)) {
			return empty();
		}
		return getEdges().stream()
				.filter(edge -> edge.contains(start))
				.filter(edge -> edge.contains(end))
				.findAny();
	}
}
