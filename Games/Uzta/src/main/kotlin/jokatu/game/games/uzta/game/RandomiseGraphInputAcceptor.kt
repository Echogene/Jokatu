package jokatu.game.games.uzta.game

import jokatu.game.event.GameEvent
import jokatu.game.games.uzta.event.GraphUpdatedEvent
import jokatu.game.games.uzta.graph.*
import jokatu.game.games.uzta.input.RandomiseGraphInput
import jokatu.game.games.uzta.player.UztaPlayer
import jokatu.game.input.AbstractInputAcceptor
import ophelia.collections.set.HashSet
import ophelia.graph.BiGraph
import java.util.*
import java.util.Arrays.stream
import java.util.stream.Collectors.toSet
import java.util.stream.IntStream.range
import java.util.stream.IntStream.rangeClosed

/**
 * Randomise the graph whenever a user wants to.
 */
internal class RandomiseGraphInputAcceptor(
		graph: ModifiableUztaGraph
) : AbstractInputAcceptor<RandomiseGraphInput, UztaPlayer, GameEvent>(RandomiseGraphInput::class, UztaPlayer::class) {

	private val graph: BiGraph<Node, LineSegment>
	private val nodes: MutableList<Node>
	private val edges: MutableSet<LineSegment>

	init {
		this.graph = graph
		this.nodes = graph.nodes
		this.edges = graph.edges
	}

	fun randomiseGraph() {
		nodes.clear()
		edges.clear()
		val random = Random()
		createNodesInRandomPositions(random)
		delaunayTrigonate()
		randomiseNodeTypes(random)
		randomiseNodeValues(random)
		fireEvent(GraphUpdatedEvent(graph))
	}

	private fun createNodesInRandomPositions(random: Random) {
		for (i in 0..49) {
			for (tries in 0..99) {
				val node = Node("node_$i", random.nextDouble() * 100, random.nextDouble() * 100)
				if (isFarEnoughAnotherNode(node)) {
					nodes.add(node)
					break
				}
			}
		}
	}

	private fun delaunayTrigonate() {
		val trigonation = HashSet<Trigon>()

		val superTrigon = Trigon(
				Node("0", 0.0, 0.0),
				Node("x", 200.0, 0.0),
				Node("y", 0.0, 200.0)
		)

		trigonation.add(superTrigon)

		for (node in nodes) {
			val badTrigons = trigonation.stream()
					.filter { trigon -> trigon.circumcircleContains(node) }
					.collect(toSet())

			badTrigons.stream()
					.peek { trigonation.remove(it) }
					.map<Set<LineSegment>> { it.edges }
					.flatMap<LineSegment> { it.stream() }
					.filter { edge -> badTrigons.stream().filter { Δ -> Δ.edges.contains(edge) }.count() == 1L }
					.map { edge -> Trigon(edge, node) }
					.forEach { trigonation.add(it) }
		}

		trigonation.stream()
				.filter { Δ -> Δ.nodes.stream().noneMatch({ superTrigon.nodes.contains(it) }) }
				.map { it.edges }
				.flatMap { it.stream() }
				.forEach { edges.add(it) }
	}

	private fun randomiseNodeTypes(random: Random) {

		val nodesToProcess = ArrayList(nodes)

		val numberOfNodes = nodesToProcess.size
		val numberOfTypes = NodeType.values().size
		val averageNumberOfEachType = numberOfNodes.toDouble() / numberOfTypes.toDouble()

		// Partition (most of) the nodes into the types to ensure that there are nodes of all types.
		val targetNumberOfEachType = averageNumberOfEachType - random.nextDouble() * numberOfNodes.toDouble() / 10.0
		stream(NodeType.values()).forEach { type ->
			for (i in 0 until targetNumberOfEachType.toInt()) {
				val node = nodesToProcess.removeAt(random.nextInt(nodesToProcess.size))
				node.type = type
			}
		}

		// Set the types of the remaining nodes to random ones.  Obviously, this will introduce bias towards certain
		// types.
		nodesToProcess.forEach { node -> node.type = NodeType.values()[random.nextInt(numberOfTypes)] }
	}

	private fun randomiseNodeValues(random: Random) {
		val nodesToProcess = ArrayList(nodes)

		// Ensure all the values are mapped to nodes.
		rangeClosed(1, Uzta.DICE_SIZE).forEach { i -> nodesToProcess.removeAt(random.nextInt(nodesToProcess.size)).addValue(i) }

		// Ensure all remaining nodes have at least one value.
		nodesToProcess.forEach { node -> node.addValue(1 + random.nextInt(Uzta.DICE_SIZE)) }

		// Add a second and maybe a third value to some nodes.
		range(0, 2).forEach { _ ->
			nodes.stream()
					.filter { _ -> random.nextBoolean() }
					.forEach { node -> node.addValue(1 + random.nextInt(Uzta.DICE_SIZE)) }
		}
	}

	private fun isFarEnoughAnotherNode(node: Node): Boolean {
		return nodes.stream()
				.allMatch { existingNode -> existingNode.distanceFrom(node) > 10 }
	}

	@Throws(Exception::class)
	override fun acceptCastInputAndPlayer(input: RandomiseGraphInput, inputter: UztaPlayer) {
		randomiseGraph()
	}
}
