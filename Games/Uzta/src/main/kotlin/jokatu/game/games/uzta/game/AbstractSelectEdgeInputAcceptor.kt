package jokatu.game.games.uzta.game

import jokatu.game.event.GameEvent
import jokatu.game.games.uzta.event.GraphUpdatedEvent
import jokatu.game.games.uzta.graph.LineSegment
import jokatu.game.games.uzta.graph.UztaGraph
import jokatu.game.games.uzta.input.SelectEdgeInput
import jokatu.game.games.uzta.player.UztaPlayer
import jokatu.game.input.AbstractInputAcceptor
import jokatu.game.input.UnacceptableInputException
import jokatu.game.turn.TurnManager
import ophelia.util.MapUtils.updateSetBasedMap
import java.util.*

abstract class AbstractSelectEdgeInputAcceptor protected constructor(protected val graph: UztaGraph, protected val turnManager: TurnManager<UztaPlayer>) : AbstractInputAcceptor<SelectEdgeInput, UztaPlayer, GameEvent>() {
	protected val ownedEdgesPerPlayer: Map<UztaPlayer, Set<LineSegment>> = HashMap()


	override val inputClass: Class<SelectEdgeInput>
		get() = SelectEdgeInput::class.java

	override val playerClass: Class<UztaPlayer>
		get() = UztaPlayer::class.java

	init {

		graph.edges.stream()
				.filter { edge -> edge.owner != null }
				.forEach { edge -> updateSetBasedMap(ownedEdgesPerPlayer, edge.owner, edge) }
	}

	@Throws(Exception::class)
	protected fun getUnownedLineSegment(input: SelectEdgeInput, inputter: UztaPlayer): LineSegment {
		turnManager.assertCurrentPlayer(inputter)

		val startId = input.startId
		val start = graph.getNode(startId)
				.orElseThrow { UnacceptableInputException("Could not find node with id ''{0}''", startId) }

		val endId = input.endId
		val end = graph.getNode(endId)
				.orElseThrow { UnacceptableInputException("Could not find node with id ''{0}''", endId) }

		val edge = graph.getEdge(start, end)
				.orElseThrow { UnacceptableInputException("Could not find edge between {0} and {1}", start, end) }

		if (edge.owner != null) {
			if (edge.owner == inputter) {
				throw UnacceptableInputException("You already own that edge!")
			} else {
				throw UnacceptableInputException("{0} already owns this edge.", edge.owner!!.name)
			}
		}
		return edge
	}

	protected fun setOwner(edge: LineSegment, inputter: UztaPlayer) {
		edge.owner = inputter
		updateSetBasedMap(ownedEdgesPerPlayer, inputter, edge)
		fireEvent(GraphUpdatedEvent(graph))
	}

	protected fun getNotNullOwnedEdgesForPlayer(inputter: UztaPlayer): Set<LineSegment> {
		return ownedEdgesPerPlayer[inputter]
				?: throw IllegalStateException("You should own at least one edge by this point.")
	}
}
