package jokatu.game.games.uzta.event

import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import jokatu.game.games.uzta.graph.NodeType
import org.springframework.stereotype.Component

@Component
class ResourcesUpdatedEventHandler : SpecificEventHandler<ResourcesUpdatedEvent>() {
	override val eventClass: Class<ResourcesUpdatedEvent>
		get() = ResourcesUpdatedEvent::class.java

	override fun handleCastEvent(game: Game<*>, event: ResourcesUpdatedEvent) {
		val player = event.player

		for (type in NodeType.values()) {
			sender.send(
					"/topic/resource.game.${game.identifier}.${player.name}.$type",
					player.getNumberOfType(type)
			)
		}
	}
}
