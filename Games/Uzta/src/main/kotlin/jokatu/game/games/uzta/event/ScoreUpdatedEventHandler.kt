package jokatu.game.games.uzta.event

import jokatu.components.stomp.PlayerScore
import jokatu.game.event.AbstractEventHandler
import jokatu.game.games.uzta.game.Uzta
import jokatu.game.games.uzta.graph.UztaGraph
import jokatu.game.games.uzta.player.UztaPlayer
import ophelia.kotlin.number.AnnotatedInt
import ophelia.kotlin.number.AnnotatedSum
import ophelia.kotlin.number.IntFrom
import org.springframework.stereotype.Component
import java.util.function.Predicate.isEqual
import java.util.stream.Collectors.toSet

/**
 * When the [graph is updated][GraphUpdatedEvent], update each player's score.
 */
@Component
class ScoreUpdatedEventHandler : AbstractEventHandler<Uzta, GraphUpdatedEvent>(Uzta::class, GraphUpdatedEvent::class) {
	override fun handleCastGameAndEvent(game: Uzta, event: GraphUpdatedEvent) {
		game.getPlayers().forEach { player ->
			sender.send(PlayerScore(game, player), calculateScore(player, game.finalisedGraph))
		}
	}

	private fun calculateScore(player: UztaPlayer, graph: UztaGraph): AnnotatedInt {
		val ownedEdges = graph.edges.stream()
				.map { it.owner }
				.filter(isEqual(player))
				.collect(toSet())

		val ownedEdgesNumber = IntFrom(ownedEdges.size, "from owned edges")

		return AnnotatedSum(
				ownedEdgesNumber
		)
	}
}