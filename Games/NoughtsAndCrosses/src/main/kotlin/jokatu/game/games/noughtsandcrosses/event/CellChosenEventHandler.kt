package jokatu.game.games.noughtsandcrosses.event

import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import org.springframework.stereotype.Component

import java.text.MessageFormat.format

/**
 * Private events should be forwarded to the users they specify.
 * @author Steven Weston
 */
@Component
class CellChosenEventHandler : SpecificEventHandler<CellChosenEvent>() {

	override val eventClass: Class<CellChosenEvent>
		get() = CellChosenEvent::class.java

	override fun handleCastEvent(game: Game<*>, event: CellChosenEvent) {

		sender.send(
				format("/topic/substatus.game.{0}.cell_{1}", game.identifier, event.cell),
				event.getNoughtOrCross()
		)
		sender.send(
				format("/topic/substatus.game.{0}.lines", game.identifier),
				event.lines
		)
	}
}
