package jokatu.game.games.rockpaperscissors.input;

import jokatu.game.games.rockpaperscissors.game.RockPaperScissors;
import jokatu.game.input.Input;

public class RockPaperScissorsInput implements Input {

	private final RockPaperScissors choice;

	public RockPaperScissorsInput(RockPaperScissors choice) {
		this.choice = choice;
	}

	public RockPaperScissors getChoice() {
		return choice;
	}
}
