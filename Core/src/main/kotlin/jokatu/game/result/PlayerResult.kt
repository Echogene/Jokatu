package jokatu.game.result

import jokatu.game.event.PublicGameEvent
import jokatu.game.event.StageOverEvent
import jokatu.game.player.Player

import java.text.MessageFormat
import java.util.stream.Collectors

/**
 * An endgame result for multiple players.  When a team wins, or multiple players draw, the set of players will have
 * more than one element.
 */
class PlayerResult(result: Result, players: Collection<Player>) : StageOverEvent(), PublicGameEvent {

	override val message: String

	init {

		val playerNames = players.stream()
				.map({ it.name })
				.collect(Collectors.joining(", "))
		message = MessageFormat.format("{0} {1}.", playerNames, result.get3rdPersonPresent(players.size))
	}
}
