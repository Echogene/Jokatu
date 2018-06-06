package jokatu.game.games.uzta.event

import jokatu.game.event.AbstractEventHandler
import jokatu.game.games.uzta.game.Uzta
import jokatu.game.games.uzta.graph.UztaGraph
import jokatu.game.games.uzta.player.UztaPlayer
import org.springframework.stereotype.Component
import java.util.function.Predicate.isEqual
import java.util.stream.Collectors.joining

/**
 * When the [graph is updated][GraphUpdatedEvent], update each player's score.
 */
@Component
class ScoreUpdatedEventHandler : AbstractEventHandler<Uzta, GraphUpdatedEvent>(Uzta::class, GraphUpdatedEvent::class) {
	override fun handleCastGameAndEvent(game: Uzta, event: GraphUpdatedEvent) {
		game.getPlayers().forEach { player ->
			sender.send(
					"/topic/score.game.${game.identifier}.${player.name}",
					calculateScore(player, game.graph)
			)
		}
	}

	private fun calculateScore(player: UztaPlayer, graph: UztaGraph): AnnotatedInt {
		val ownedEdges = graph.edges.stream()
				.map { it.owner }
				.filter(isEqual(player))
				.count().toInt()
		return IntFrom(ownedEdges, "from owned edges")
	}
}

interface AnnotatedInt {
	val number: Int
	val annotation: String
}

class IntFrom(override val number: Int, from: String): AnnotatedInt {
	override val annotation = "$number $from"
}

class AnnotatedSum(summands: Collection<AnnotatedInt>): AnnotatedInt {
	override val number = summands.sumBy { it.number }

	override val annotation = "$number, the sum of" +
			summands.stream()
					.map { "\t${it.annotation}" }
					.collect(joining("\n"))
}