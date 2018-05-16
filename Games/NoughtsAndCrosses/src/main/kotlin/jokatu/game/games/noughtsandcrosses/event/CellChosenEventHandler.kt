package jokatu.game.games.noughtsandcrosses.event

import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import org.springframework.stereotype.Component

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
				"/topic/substatus.game.${game.identifier}.cell_${event.cell}",
				event.getNoughtOrCross()
		)
		sender.send(
				"/topic/substatus.game.${game.identifier}.lines",
				event.lines
		)
	}
}
