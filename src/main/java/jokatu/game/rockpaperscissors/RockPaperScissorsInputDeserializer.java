package jokatu.game.rockpaperscissors;

import jokatu.game.factory.Factory;
import jokatu.game.factory.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;

import static jokatu.game.rockpaperscissors.RockPaperScissorsGame.ROCK_PAPER_SCISSORS;

@Factory(gameName = ROCK_PAPER_SCISSORS)
public class RockPaperScissorsInputDeserializer implements InputDeserialiser<RockPaperScissorsInput> {
	@NotNull
	@Override
	public RockPaperScissorsInput deserialise(String json) {
		return new RockPaperScissorsInput(RockPaperScissors.valueOf(json));
	}
}
