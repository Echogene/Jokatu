package jokatu.game.games.rockpaperscissors;

import jokatu.game.input.Input;

public class RockPaperScissorsInput implements Input {

	private final RockPaperScissors choice;

	protected RockPaperScissorsInput(RockPaperScissors choice) {
		this.choice = choice;
	}

	public RockPaperScissors getChoice() {
		return choice;
	}
}
