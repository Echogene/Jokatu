package jokatu.game.games.sevens.input;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SkipInputDeserialiser implements InputDeserialiser<SkipInput> {
	@NotNull
	@Override
	public SkipInput deserialise(@NotNull Map<String, Object> json) throws DeserialisationException {
		if (!json.containsKey("skip")) {
			throw new DeserialisationException(json, "Did not contain the key 'skip'.");
		}
		return new SkipInput();
	}
}
