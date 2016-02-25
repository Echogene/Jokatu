package jokatu.game.games.rockpaperscissors.input;

import jokatu.game.factory.input.InputDeserialiser;
import jokatu.game.games.rockpaperscissors.game.RockPaperScissors;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class RockPaperScissorsInputDeserializer implements InputDeserialiser<RockPaperScissorsInput> {
	@NotNull
	@Override
	public RockPaperScissorsInput deserialise(String json) {
		return new RockPaperScissorsInput(RockPaperScissors.valueOf(json));
	}
}
