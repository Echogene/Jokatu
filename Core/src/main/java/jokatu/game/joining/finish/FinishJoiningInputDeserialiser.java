package jokatu.game.joining.finish;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FinishJoiningInputDeserialiser implements InputDeserialiser<FinishJoiningInput> {
	@NotNull
	@Override
	public FinishJoiningInput deserialise(@NotNull Map<String, Object> json) throws DeserialisationException {
		if (!json.containsKey("start")) {
			throw new DeserialisationException(json, "Did not contain the key 'start'.");
		} else {
			return new FinishJoiningInput();
		}
	}
}
