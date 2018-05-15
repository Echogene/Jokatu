package jokatu.game.games.noughtsandcrosses.event

import jokatu.game.event.GameEvent
import jokatu.game.games.noughtsandcrosses.game.Line
import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross

class CellChosenEvent(val cell: Int, private val noughtOrCross: NoughtOrCross, val lines: List<Line>) : GameEvent {

	fun getNoughtOrCross(): String {
		return noughtOrCross.toString()
	}
}
