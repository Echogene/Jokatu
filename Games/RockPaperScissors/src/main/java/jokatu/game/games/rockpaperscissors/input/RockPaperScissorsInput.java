package jokatu.game.games.rockpaperscissors.input;

import jokatu.game.games.rockpaperscissors.game.RockPaperScissors;
import jokatu.game.input.Input;

class RockPaperScissorsInput implements Input {

	private final RockPaperScissors choice;

	RockPaperScissorsInput(RockPaperScissors choice) {
		this.choice = choice;
	}

	RockPaperScissors getChoice() {
		return choice;
	}
}
