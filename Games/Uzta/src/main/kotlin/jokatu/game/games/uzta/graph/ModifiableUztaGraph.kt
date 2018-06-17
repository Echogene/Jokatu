package jokatu.game.games.uzta.graph

import ophelia.kotlin.graph.MutableUndirectedGraph

class ModifiableUztaGraph : UztaGraph, MutableUndirectedGraph<Node, LineSegment> {
	override val nodes: MutableList<Node> = ArrayList()
	override val edges: MutableSet<LineSegment> = HashSet()
}
