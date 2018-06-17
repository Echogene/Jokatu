package jokatu.game.games.uzta.graph

class FinalisedUztaGraph(
		graph: UztaGraph
): UztaGraph {
	override val nodes = graph.nodes
	override val edges = graph.edges
}