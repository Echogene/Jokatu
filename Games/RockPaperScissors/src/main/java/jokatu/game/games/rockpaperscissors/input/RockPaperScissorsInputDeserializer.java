package jokatu.game.games.rockpaperscissors.input;

import jokatu.game.games.rockpaperscissors.game.RockPaperScissors;
import jokatu.game.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RockPaperScissorsInputDeserializer implements InputDeserialiser<RockPaperScissorsInput> {
	@NotNull
	@Override
	public RockPaperScissorsInput deserialise(Map<String, Object> json) {
		return new RockPaperScissorsInput(RockPaperScissors.valueOf((String) json.get("choice")));
	}
}
