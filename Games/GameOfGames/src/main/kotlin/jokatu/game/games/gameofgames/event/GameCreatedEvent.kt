package jokatu.game.games.gameofgames.event

import jokatu.game.event.PublicGameEvent
import jokatu.game.player.StandardPlayer

import java.text.MessageFormat


class GameCreatedEvent(val player: StandardPlayer, val gameName: String) : PublicGameEvent {

	override val message: String
		get() = MessageFormat.format("{0} created a {1}", player.name, gameName)
}
