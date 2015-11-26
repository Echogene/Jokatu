package jokatu.game.games.rockpaperscissors;

import jokatu.game.factory.input.InputDeserialiser;
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
