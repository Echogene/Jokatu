package jokatu.game.games.uzta.game

import jokatu.game.event.StandardPublicGameEvent
import jokatu.game.games.uzta.graph.LineSegment
import jokatu.game.games.uzta.graph.NodeType
import jokatu.game.games.uzta.graph.UztaGraph
import jokatu.game.games.uzta.input.SelectEdgeInput
import jokatu.game.games.uzta.player.UztaPlayer
import jokatu.game.input.UnacceptableInputException
import jokatu.game.turn.TurnManager
import ophelia.collections.bag.BaseIntegerBag
import ophelia.collections.bag.HashBag
import ophelia.tuple.Pair
import java.util.*
import java.util.stream.Collectors


class MainStageSelectEdgeInputAcceptor internal constructor(
		graph: UztaGraph,
		turnManager: TurnManager<UztaPlayer>,
		private val resourceDistributor: ResourceDistributor
) : AbstractSelectEdgeInputAcceptor(graph, turnManager) {

	private val die: Random

	init {
		turnManager.observe { _ -> roll() }

		die = Random()
	}

	private fun roll() {
		val roll = 1 + die.nextInt(Uzta.DICE_SIZE)
		val currentPlayer = turnManager.currentPlayer!!
		fireEvent(StandardPublicGameEvent("${currentPlayer.name} rolled $roll"))

		resourceDistributor.distributeResourcesForRoll(roll)
	}

	@Throws(Exception::class)
	override fun acceptCastInputAndPlayer(input: SelectEdgeInput, inputter: UztaPlayer) {
		val edge = getUnownedLineSegment(input, inputter)

		val edgeCost = getCost(edge)
		val resourcesLeft = inputter.getResourcesLeftAfter(edgeCost)
		if (resourcesLeft.hasLackingItems()) {
			val neededResources = resourcesLeft.stream()
					.filter { pair -> pair.right < 0 }
					.map { this.presentNeededResources(it) }
					.collect(Collectors.joining(", "))
			throw UnacceptableInputException("You can''t afford that edge.  You still need $neededResources.")
		}

		setOwner(edge, inputter)
		inputter.giveResources(edgeCost.inverse)
	}

	private fun presentNeededResources(pair: Pair<NodeType, Int>): String {
		val numberNeeded = -pair.right
		return pair.left.getNumber(numberNeeded)
	}

	private fun getCost(edge: LineSegment): BaseIntegerBag<NodeType> {
		val bag = HashBag<NodeType>()
		edge.forEach { node -> bag.modifyNumberOf(node.type!!, node.values.size) }
		Arrays.stream(NodeType.values()).forEach { bag.addOne(it) }
		return bag
	}
}
