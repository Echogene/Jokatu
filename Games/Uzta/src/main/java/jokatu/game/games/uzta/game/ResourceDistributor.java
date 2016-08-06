package jokatu.game.games.uzta.game;

import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.Node;
import jokatu.game.games.uzta.graph.UztaGraph;
import jokatu.game.games.uzta.player.UztaPlayer;
import ophelia.util.MapUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptySet;
import static ophelia.util.FunctionUtils.notNull;

class ResourceDistributor {

	private final UztaGraph graph;
	private final Map<Node, Set<LineSegment>> nodeToOwningEdges;

	ResourceDistributor(UztaGraph graph) {
		this.graph = graph;

		nodeToOwningEdges = new HashMap<>();

		graph.getEdges().stream()
				.forEach(edge -> edge.forEach(node -> MapUtils.updateSetBasedMap(nodeToOwningEdges, node, edge)));
	}

	void distributeStartingResources() {
		graph.getEdges().stream()
				.filter(edge -> edge.getOwner() != null)
				.forEach(this::giveStartingResourcesForEdge);
	}

	private void giveStartingResourcesForEdge(LineSegment edge) {
		UztaPlayer owner = edge.getOwner();
		assert owner != null;
		edge.forEach(node -> giveResourceFromNode(owner, node));
	}

	void distributeResourcesForRoll(int roll) {
		graph.getNodes().stream()
				.filter(node -> node.getValues().contains(roll))
				.forEach(node -> {
					Set<LineSegment> edges = nodeToOwningEdges.getOrDefault(node, emptySet());
					edges.stream()
							.map(LineSegment::getOwner)
							.filter(notNull())
							.forEach(owner -> giveResourceFromNode(owner, node));
				});
	}

	private void giveResourceFromNode(@NotNull UztaPlayer owner, @NotNull Node node) {
		owner.giveResources(node.getType(), 1);
	}
}
