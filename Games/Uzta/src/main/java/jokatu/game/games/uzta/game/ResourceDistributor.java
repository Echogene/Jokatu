package jokatu.game.games.uzta.game;

import jokatu.game.games.uzta.event.GraphUpdatedEvent;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.Node;
import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.games.uzta.graph.UztaGraph;
import jokatu.game.games.uzta.player.UztaPlayer;
import ophelia.collections.bag.ModifiableIntegerBag;
import ophelia.event.observable.AbstractSynchronousObservable;
import ophelia.tuple.Pair;
import ophelia.util.MapUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.*;
import static ophelia.collections.bag.BagCollectors.toBag;
import static ophelia.util.function.PredicateUtils.notNull;

class ResourceDistributor extends AbstractSynchronousObservable<GraphUpdatedEvent> {

	private final UztaGraph graph;
	private final Map<Node, Set<LineSegment>> nodeToOwningEdges;

	ResourceDistributor(UztaGraph graph) {
		this.graph = graph;

		nodeToOwningEdges = new HashMap<>();

		graph.getEdges().stream()
				.forEach(edge -> edge.forEach(node -> MapUtils.updateSetBasedMap(nodeToOwningEdges, node, edge)));
	}

	void distributeStartingResources() {
		Map<UztaPlayer, ModifiableIntegerBag<NodeType>> startingResources = graph.getEdges().stream()
				.filter(edge -> edge.getOwner() != null)
				.flatMap(this::getStartingResourcesForEdge)
				.collect(groupingBy(Pair::getLeft, mapping(Pair::getRight, toBag())));
		startingResources.forEach(UztaPlayer::giveResources);
	}

	private Stream<Pair<UztaPlayer, NodeType>> getStartingResourcesForEdge(@NotNull LineSegment edge) {
		UztaPlayer owner = edge.getOwner();
		assert owner != null;

		return edge.stream()
				.map(Node::getType)
				.map(type -> new Pair<>(owner, type));
	}

	void distributeResourcesForRoll(int roll) {

		Map<Boolean, List<Node>> nodesByRoll = graph.getNodes().stream()
				.collect(partitioningBy(node -> node.getValues().contains(roll)));

		List<Node> nodesForRoll = nodesByRoll.getOrDefault(true, emptyList());
		List<Node> nodesNotForRoll = nodesByRoll.getOrDefault(false, emptyList());

		nodesForRoll.forEach(node -> node.setHighlighted(true));
		nodesNotForRoll.forEach(node -> node.setHighlighted(false));

		fireEvent(new GraphUpdatedEvent(graph));

		Map<UztaPlayer, ModifiableIntegerBag<NodeType>> resources = nodesForRoll.stream()
				.flatMap(this::getResourcesForNode)
				.collect(groupingBy(Pair::getLeft, mapping(Pair::getRight, toBag())));
		resources.forEach(UztaPlayer::giveResources);
	}

	private Stream<Pair<UztaPlayer, NodeType>> getResourcesForNode(@NotNull Node node) {
		Set<LineSegment> edges = nodeToOwningEdges.getOrDefault(node, emptySet());
		return edges.stream()
				.map(LineSegment::getOwner)
				.filter(notNull())
				.map(owner -> new Pair<>(owner, node.getType()));
	}
}
