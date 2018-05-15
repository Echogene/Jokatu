package jokatu.game.games.uzta.graph

import ophelia.collections.list.ArrayList
import ophelia.collections.set.HashSet
import ophelia.graph.ModifiableBiGraph

class ModifiableUztaGraph : UztaGraph, ModifiableBiGraph<Node, LineSegment> {
	private val nodes: ArrayList<Node> = ArrayList()
	private val edges: HashSet<LineSegment> = HashSet()

	override fun getNodes() = nodes
	override fun getEdges() = edges
}
