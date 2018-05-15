package jokatu.game.games.uzta.event

import jokatu.game.event.PublicGameEvent
import jokatu.game.games.uzta.graph.NodeType
import jokatu.game.games.uzta.player.UztaPlayer
import ophelia.collections.bag.BaseIntegerBag
import java.text.MessageFormat
import java.util.*
import java.util.stream.Collectors.joining

class ResourcesUpdatedEvent(val player: UztaPlayer, private val resources: BaseIntegerBag<NodeType>) : PublicGameEvent {

	override val message: String
		get() {
			val messages = ArrayList<String>()
			if (resources.hasSurplusItems()) {
				messages.add(MessageFormat.format(
						"gained {0}",
						resources.surplusItems.stream()
								.map { entry -> entry.left.getNumber(entry.right) }
								.collect(joining(", "))
				))
			}
			if (resources.hasLackingItems()) {
				messages.add(MessageFormat.format(
						"paid {0}",
						resources.lackingItems.stream()
								.map { entry -> entry.left.getNumber(-entry.right) }
								.collect(joining(", "))
				))
			}

			return player.toString() + messages.stream().collect(joining(" and ", " ", ""))
		}
}
