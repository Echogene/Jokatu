package jokatu.game.games.uzta.game

import jokatu.game.Game
import jokatu.game.GameID
import jokatu.game.games.uzta.graph.ModifiableUztaGraph
import jokatu.game.games.uzta.player.UztaPlayer
import jokatu.game.stage.GameOverStage
import jokatu.game.stage.MinAndMaxJoiningStage
import jokatu.game.stage.machine.SequentialStageMachine
import jokatu.game.status.StandardTextStatus
import java.util.*

/**
 * A game played on an abstract graph where players harvest abstract resources produced by the graph in order to build
 * more things on the graph.
 */
class Uzta internal constructor(identifier: GameID) : Game<UztaPlayer>(identifier) {

	val graph = ModifiableUztaGraph()

	private val status = StandardTextStatus()

	override val gameName: String
		get() = UZTA

	init {
		stageMachine = SequentialStageMachine(
				{ MinAndMaxJoiningStage(UztaPlayer::class, players, 1, 6, status) },
				{ SetupStage(graph, players, status) },
				{ FirstPlacementStage(graph, determineTurnOrder(players), status) },
				{ MainStage(graph, players, determineTurnOrder(players), status) },
				{ GameOverStage(status) }
		)

		status.observe(::fireEvent)
	}

	private fun determineTurnOrder(players: Map<String, UztaPlayer>): List<UztaPlayer> {
		return ArrayList(players.values)
	}

	companion object {
		const val UZTA = "Uzta"

		internal const val DICE_SIZE = 12
	}
}
