package jokatu.game.games.uzta.game

import jokatu.game.MultiInputStage
import jokatu.game.games.uzta.graph.ModifiableUztaGraph
import jokatu.game.games.uzta.player.UztaPlayer
import jokatu.game.input.EndStageInputAcceptor
import jokatu.game.status.StandardTextStatus
import ophelia.collections.set.UnmodifiableSet
import java.util.*

/**
 * The stage of [Uzta] where the game is set up.
 */
class SetupStage internal constructor(graph: ModifiableUztaGraph, players: Map<String, UztaPlayer>, private val status: StandardTextStatus) : MultiInputStage() {

	private val randomiseGraphInputAcceptor: RandomiseGraphInputAcceptor = RandomiseGraphInputAcceptor(graph)

	init {
		addInputAcceptor(randomiseGraphInputAcceptor)

		addInputAcceptor(EndStageInputAcceptor(UztaPlayer::class, UnmodifiableSet(players.values)))

		assignColours(players.values)
	}

	private fun assignColours(players: Collection<UztaPlayer>) {
		val orderedPlayers = ArrayList(players)
		orderedPlayers.forEachIndexed { i, uztaPlayer -> uztaPlayer.colour = UztaColour.values()[i] }
	}

	override fun start() {
		randomiseGraphInputAcceptor.randomiseGraph()

		status.text = "Waiting for a player to accept a random graph."
	}
}
