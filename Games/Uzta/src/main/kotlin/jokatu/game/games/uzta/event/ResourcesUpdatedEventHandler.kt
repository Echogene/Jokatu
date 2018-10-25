package jokatu.game.games.uzta.event

import jokatu.components.stomp.Topic
import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import jokatu.game.games.uzta.graph.NodeType
import jokatu.game.games.uzta.player.UztaPlayer
import org.springframework.stereotype.Component

@Component
class ResourcesUpdatedEventHandler : SpecificEventHandler<ResourcesUpdatedEvent>(ResourcesUpdatedEvent::class) {
	override fun handleCastEvent(game: Game<*>, event: ResourcesUpdatedEvent) {
		val player = event.player

		for (type in NodeType.values()) {
			sender.send(PlayerResources(game, player, type), player.getNumberOfType(type))
		}
	}
}

private class PlayerResources(
		game: Game<*>,
		player: UztaPlayer,
		type: NodeType
): Topic<Int>("resource.game.${game.identifier}.${player.name}.$type")