package jokatu.game.rockpaperscissors;

import jokatu.game.input.AbstractInput;

public class RockPaperScissorsInput extends AbstractInput<RockPaperScissorsPlayer> {

	private final RockPaperScissors choice;

	protected RockPaperScissorsInput(RockPaperScissorsPlayer player, RockPaperScissors choice) {
		super(player);
		this.choice = choice;
	}

	public RockPaperScissors getChoice() {
		return choice;
	}
}
