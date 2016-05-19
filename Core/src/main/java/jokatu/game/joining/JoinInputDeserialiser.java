package jokatu.game.joining;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JoinInputDeserialiser implements InputDeserialiser<JoinInput> {
	@NotNull
	@Override
	public JoinInput deserialise(@NotNull Map<String, Object> json) throws DeserialisationException {
		if (!json.containsKey("join")) {
			throw new DeserialisationException(json, "Did not contain the key 'join'.");
		} else {
			return new JoinInput();
		}
	}
}
