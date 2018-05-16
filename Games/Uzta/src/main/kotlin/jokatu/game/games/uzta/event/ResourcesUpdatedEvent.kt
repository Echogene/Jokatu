package jokatu.game.games.uzta.event

import jokatu.game.event.PublicGameEvent
import jokatu.game.games.uzta.graph.NodeType
import jokatu.game.games.uzta.player.UztaPlayer
import ophelia.collections.bag.BaseIntegerBag
import java.util.*
import java.util.stream.Collectors.joining

class ResourcesUpdatedEvent(val player: UztaPlayer, private val resources: BaseIntegerBag<NodeType>) : PublicGameEvent {

	override val message: String
		get() {
			val messages = ArrayList<String>()
			if (resources.hasSurplusItems()) {
				val gainedResources = resources.surplusItems.stream()
						.map { entry -> entry.left.getNumber(entry.right) }
						.collect(joining(", "))
				messages.add("gained $gainedResources")
			}
			if (resources.hasLackingItems()) {
				val paidResources = resources.lackingItems.stream()
						.map { entry -> entry.left.getNumber(-entry.right) }
						.collect(joining(", "))
				messages.add("paid $paidResources")
			}

			return player.toString() + messages.stream().collect(joining(" and ", " ", ""))
		}
}
