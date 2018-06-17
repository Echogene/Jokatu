package jokatu.game.games.uzta.game

import jokatu.game.games.uzta.event.GraphUpdatedEvent
import jokatu.game.games.uzta.graph.LineSegment
import jokatu.game.games.uzta.graph.Node
import jokatu.game.games.uzta.graph.NodeType
import jokatu.game.games.uzta.graph.UztaGraph
import jokatu.game.games.uzta.player.UztaPlayer
import ophelia.collections.bag.BagCollectors.toBag
import ophelia.event.observable.AbstractSynchronousObservable
import ophelia.tuple.Pair
import ophelia.util.function.PredicateUtils.notNull
import java.util.*
import java.util.Collections.emptyList
import java.util.Collections.emptySet
import java.util.stream.Collectors.*
import java.util.stream.Stream
import kotlin.collections.HashSet

typealias UserTappedNode = Pair<UztaPlayer, NodeType>

internal class ResourceDistributor(private val graph: UztaGraph) : AbstractSynchronousObservable<GraphUpdatedEvent>() {
	private val nodeToOwningEdges: MutableMap<Node, MutableSet<LineSegment>> = HashMap()

	init {
		graph.edges.stream()
				.forEach { edge ->
					edge.forEach { node ->
						nodeToOwningEdges.computeIfAbsent(node) { HashSet() }.add(edge)
					}
				}
	}

	fun distributeStartingResources() {
		val startingResources = graph.edges.stream()
				.filter { edge -> edge.owner != null }
				.flatMap { getStartingResourcesForEdge(it) }
				.collect(groupingBy({ n: UserTappedNode -> n.left }, mapping({ it.right }, toBag<NodeType>())))
		startingResources.forEach({ obj, givenResources -> obj.giveResources(givenResources) })
	}

	private fun getStartingResourcesForEdge(edge: LineSegment): Stream<Pair<UztaPlayer, NodeType>> {
		val owner = edge.owner!!

		return edge.stream()
				.map<NodeType> { it.type }
				.map { type -> Pair(owner, type) }
	}

	fun distributeResourcesForRoll(roll: Int) {

		val nodesByRoll = graph.nodes.stream()
				.collect(partitioningBy { node -> node.values.contains(roll) })

		val nodesForRoll = nodesByRoll.getOrDefault(true, emptyList())
		val nodesNotForRoll = nodesByRoll.getOrDefault(false, emptyList())

		nodesForRoll.forEach { node -> node.isHighlighted = true }
		nodesNotForRoll.forEach { node -> node.isHighlighted = false }

		fireEvent(GraphUpdatedEvent(graph))

		val resources = nodesForRoll.stream()
				.flatMap { getResourcesForNode(it) }
				.collect(groupingBy({ n: UserTappedNode -> n.left }, mapping({ it.right }, toBag<NodeType>())))
		resources.forEach { obj, givenResources -> obj.giveResources(givenResources) }
	}

	private fun getResourcesForNode(node: Node): Stream<UserTappedNode> {
		val edges = nodeToOwningEdges.getOrDefault(node, emptySet())
		return edges.stream()
				.map { it.owner }
				.filter(notNull())
				.map { owner -> Pair(owner!!, node.type!!) }
	}
}
