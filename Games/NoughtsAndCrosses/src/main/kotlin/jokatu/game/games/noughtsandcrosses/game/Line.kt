package jokatu.game.games.noughtsandcrosses.game

import ophelia.collections.list.UnmodifiableList

// This is converted to JSON using Jackson.
class Line internal constructor(cells: UnmodifiableList<Int>) {
	val start: String = "cell_${cells.get(0)}"
	val end: String = "cell_${cells.get(cells.size() - 1)}"
}
