package jokatu.game.games.gameofgames.input;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CreateGameInputDeserialiser implements InputDeserialiser<CreateGameInput> {
	@NotNull
	@Override
	public CreateGameInput deserialise(Map<String, Object> json) throws DeserialisationException {
		if (!json.containsKey("gameName")) {
			throw new DeserialisationException(json, "Did not contain the key 'gameName'.");
		}
		Object value = json.get("gameName");
		if (!(value instanceof String)) {
			throw new DeserialisationException(json, "The value for 'gameName' was not a string.");
		}
		String gameName = (String) value;
		return new CreateGameInput(gameName);
	}
}
