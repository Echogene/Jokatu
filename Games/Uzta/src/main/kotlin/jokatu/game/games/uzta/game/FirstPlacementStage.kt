package jokatu.game.games.uzta.game

import jokatu.game.event.GameEvent
import jokatu.game.event.StageOverEvent
import jokatu.game.games.uzta.graph.UztaGraph
import jokatu.game.games.uzta.input.SelectEdgeInput
import jokatu.game.games.uzta.player.UztaPlayer
import jokatu.game.input.UnacceptableInputException
import jokatu.game.stage.Stage
import jokatu.game.status.StandardTextStatus
import jokatu.game.turn.TurnManager
import ophelia.collections.list.UnmodifiableList
import java.util.Collections.emptySet

/**
 * The stage where players determine their starting positions.
 */
class FirstPlacementStage internal constructor(
		graph: UztaGraph,
		playersInOrder: List<UztaPlayer>,
		private val status: StandardTextStatus
) : AbstractSelectEdgeInputAcceptor(graph, TurnManager(playersInOrder)), Stage<GameEvent> {

	val playersInOrder: UnmodifiableList<UztaPlayer> = UnmodifiableList(playersInOrder)

	init {
		this.turnManager.observe { this.fireEvent(it) }
	}

	override fun start() {
		turnManager.next()

		status.text = "Waiting for players to choose their starting edges."
	}

	@Throws(Exception::class)
	override fun acceptCastInputAndPlayer(input: SelectEdgeInput, inputter: UztaPlayer) {
		val edge = getUnownedLineSegment(input, inputter)

		val alreadyOwnedEdges = ownedEdgesPerPlayer.getOrDefault(inputter, emptySet())
		if (alreadyOwnedEdges.size > 1) {
			throw UnacceptableInputException("You already own more than one edge.  Don't be greedy!")
		}

		setOwner(edge, inputter)

		val ownedEdges = getNotNullOwnedEdgesForPlayer(inputter)
		val minOwnedEdges = playersInOrder.stream()
				.map { player -> ownedEdgesPerPlayer.getOrDefault(player, emptySet()) }
				.mapToInt { it.size }
				.min()
				.orElse(0)
		if (minOwnedEdges > 1) {
			// Everyone now owns two edges.
			fireEvent(StageOverEvent())
		} else if (ownedEdges.size < 2) {
			if (minOwnedEdges > 0) {
				// Everyone has input once, so go again.
				turnManager.playAgain()
			} else {
				turnManager.next()
			}
		} else {
			turnManager.previous()
		}
	}
}
