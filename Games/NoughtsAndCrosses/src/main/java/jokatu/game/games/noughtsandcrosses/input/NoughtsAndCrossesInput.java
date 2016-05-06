package jokatu.game.games.noughtsandcrosses.input;

import jokatu.game.input.Input;

class NoughtsAndCrossesInput implements Input {

	private final Integer cellId;

	NoughtsAndCrossesInput(Integer cellId) {
		this.cellId = cellId;
	}

	Integer getCellId() {
		return cellId;
	}
}
