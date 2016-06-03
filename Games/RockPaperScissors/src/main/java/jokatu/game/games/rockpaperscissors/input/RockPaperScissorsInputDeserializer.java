package jokatu.game.games.rockpaperscissors.input;

import jokatu.game.games.rockpaperscissors.game.RockPaperScissors;
import jokatu.game.input.TypedSingleKeyInputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RockPaperScissorsInputDeserializer extends TypedSingleKeyInputDeserialiser<String, RockPaperScissorsInput> {
	@NotNull
	@Override
	public RockPaperScissorsInput deserialiseTypedSingleValue(@NotNull Map<String, Object> json, @NotNull String choice) {
		return new RockPaperScissorsInput(RockPaperScissors.valueOf(choice));
	}

	@NotNull
	@Override
	protected String getKeyName() {
		return "choice";
	}

	@NotNull
	@Override
	protected Class<String> getType() {
		return String.class;
	}
}
