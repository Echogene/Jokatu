package jokatu.game.games.uzta.game

import jokatu.game.MultiInputStage
import jokatu.game.games.uzta.graph.UztaGraph
import jokatu.game.games.uzta.player.UztaPlayer
import jokatu.game.input.endturn.EndTurnInputAcceptor
import jokatu.game.status.StandardTextStatus
import jokatu.game.turn.TurnManager

class MainStage internal constructor(
		graph: UztaGraph,
		players: Map<String, UztaPlayer>,
		playersInOrder: List<UztaPlayer>,
		private val status: StandardTextStatus
) : MultiInputStage() {

	private val resourceDistributor: ResourceDistributor = ResourceDistributor(graph)
	private val turnManager: TurnManager<UztaPlayer>

	init {
		resourceDistributor.observe { this.fireEvent(it) }

		turnManager = TurnManager(playersInOrder)

		val mainStageSelectEdgeInputAcceptor = MainStageSelectEdgeInputAcceptor(graph, turnManager, resourceDistributor)
		addInputAcceptor(mainStageSelectEdgeInputAcceptor)

		val initialTradeRequestAcceptor = InitialTradeRequestAcceptor(players)
		addInputAcceptor(initialTradeRequestAcceptor)

		val endTurnInputAcceptor = EndTurnInputAcceptor(turnManager, UztaPlayer::class.java)
		addInputAcceptor(endTurnInputAcceptor)

		turnManager.observe { e ->
			status.setText("Waiting for {0} to buy edges or pass.", e.newPlayer)
			// Forward the event.
			fireEvent(e)
		}

		playersInOrder.forEach { player -> player.observe({ this.fireEvent(it) }) }
	}

	override fun start() {
		resourceDistributor.distributeStartingResources()

		turnManager.next()
	}
}
