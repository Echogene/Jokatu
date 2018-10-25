package jokatu.game.games.noughtsandcrosses.event

import jokatu.components.stomp.Topic
import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import jokatu.game.games.noughtsandcrosses.game.Line
import org.springframework.stereotype.Component

/**
 * Private events should be forwarded to the users they specify.
 * @author Steven Weston
 */
@Component
class CellChosenEventHandler : SpecificEventHandler<CellChosenEvent>(CellChosenEvent::class) {
	override fun handleCastEvent(game: Game<*>, event: CellChosenEvent) {
		sender.send(Cell(game, event.cell), event.getNoughtOrCross())
		sender.send(Lines(game), event.lines)
	}
}

private class Cell(game: Game<*>, cell: Int): Topic<String>("substatus.game.${game.identifier}.cell_$cell")

private class Lines(game: Game<*>): Topic<List<Line>>("substatus.game.${game.identifier}.lines")