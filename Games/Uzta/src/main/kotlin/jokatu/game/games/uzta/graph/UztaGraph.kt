package jokatu.game.games.uzta.graph

import ophelia.kotlin.graph.UndirectedGraph
import java.util.*
import java.util.Optional.empty

interface UztaGraph : UndirectedGraph<Node, LineSegment> {

	fun getNode(id: String): Optional<Node> {
		return nodes.stream()
				.filter { node -> id == node.id }
				.findAny()
	}

	fun getEdge(start: Node, end: Node): Optional<LineSegment> {
		return if (start == end) {
			empty()
		} else edges.stream()
				.filter { edge -> edge.contains(start) }
				.filter { edge -> edge.contains(end) }
				.findAny()
	}
}
