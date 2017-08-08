package jokatu.game.games.noughtsandcrosses.input;

import jokatu.game.input.Input;

public class NoughtsAndCrossesInput implements Input {

	private final Integer cellId;

	NoughtsAndCrossesInput(Integer cellId) {
		this.cellId = cellId;
	}

	public Integer getCellId() {
		return cellId;
	}
}
