package jokatu.game.games.noughtsandcrosses.input;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NoughtsAndCrossesInputDeserialiser implements InputDeserialiser<NoughtsAndCrossesInput> {
	@NotNull
	@Override
	public NoughtsAndCrossesInput deserialise(@NotNull Map<String, Object> json) throws DeserialisationException {
		return new NoughtsAndCrossesInput((Integer) json.get("choice"));
	}
}
