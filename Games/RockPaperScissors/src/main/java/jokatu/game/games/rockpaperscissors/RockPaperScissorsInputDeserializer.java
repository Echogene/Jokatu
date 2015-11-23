package jokatu.game.games.rockpaperscissors;

import jokatu.game.factory.GameComponent;
import jokatu.game.factory.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;

import static jokatu.game.games.rockpaperscissors.RockPaperScissorsGame.ROCK_PAPER_SCISSORS;

@GameComponent(gameName = ROCK_PAPER_SCISSORS)
public class RockPaperScissorsInputDeserializer implements InputDeserialiser<RockPaperScissorsInput> {
	@NotNull
	@Override
	public RockPaperScissorsInput deserialise(String json) {
		return new RockPaperScissorsInput(RockPaperScissors.valueOf(json));
	}
}
