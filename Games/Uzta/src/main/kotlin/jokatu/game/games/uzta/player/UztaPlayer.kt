package jokatu.game.games.uzta.player

import jokatu.game.event.GameEvent
import jokatu.game.games.uzta.event.ResourcesUpdatedEvent
import jokatu.game.games.uzta.game.UztaColour
import jokatu.game.games.uzta.graph.NodeType
import jokatu.game.player.Player
import ophelia.collections.bag.BagUtils
import ophelia.collections.bag.BaseIntegerBag
import ophelia.collections.bag.HashBag
import ophelia.event.observable.AbstractSynchronousObservable
import java.text.MessageFormat
import java.util.stream.Collectors.joining

class UztaPlayer(override val name: String) : AbstractSynchronousObservable<GameEvent>(), Player {
	var colour: UztaColour? = null

	private val resources = HashBag<NodeType>()

	fun giveResources(givenResources: BaseIntegerBag<NodeType>) {
		if (resources.getSum(givenResources).hasLackingItems()) {
			throw NotEnoughResourcesException(
					"{0} does not have enough resources {1}",
					name,
					BagUtils.presentBag(
							givenResources,
							{ obj, number -> obj.getNumber(number) },
							joining(", ", "to be given ", " "),
							joining(", ", "and to give ", ".")
					)
			)
		}
		resources.merge(givenResources)
		fireEvent(ResourcesUpdatedEvent(this, givenResources))
	}

	fun getResourcesLeftAfter(requiredResources: BaseIntegerBag<NodeType>): BaseIntegerBag<NodeType> {
		return resources.getDifference(requiredResources)
	}

	override fun toString(): String {
		return name
	}

	fun getNumberOfType(type: NodeType): Int {
		return resources.getNumberOf(type)
	}

	fun getResources(): BaseIntegerBag<NodeType> {
		return resources
	}

	class NotEnoughResourcesException(pattern: String, vararg arguments: Any) : RuntimeException(MessageFormat.format(pattern, *arguments))
}
