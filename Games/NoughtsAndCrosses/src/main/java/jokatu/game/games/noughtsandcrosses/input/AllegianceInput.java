package jokatu.game.games.noughtsandcrosses.input;

import jokatu.game.input.Input;

class AllegianceInput implements Input {
	private final NoughtOrCross noughtOrCross;

	AllegianceInput(NoughtOrCross noughtOrCross) {
		this.noughtOrCross = noughtOrCross;
	}

	public NoughtOrCross getNoughtOrCross() {
		return noughtOrCross;
	}
}
