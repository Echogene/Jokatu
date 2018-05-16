package jokatu.game.games.gameofgames.event

import jokatu.game.event.PublicGameEvent
import jokatu.game.player.StandardPlayer


class GameCreatedEvent(val player: StandardPlayer, val gameName: String) : PublicGameEvent {

	override val message: String
		get() = "${player.name} created a $gameName."
}
