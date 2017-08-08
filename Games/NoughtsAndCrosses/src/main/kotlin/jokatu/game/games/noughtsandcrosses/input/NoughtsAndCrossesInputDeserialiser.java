package jokatu.game.games.noughtsandcrosses.input;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.TypedSingleKeyInputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NoughtsAndCrossesInputDeserialiser extends TypedSingleKeyInputDeserialiser<Integer, NoughtsAndCrossesInput> {
	@NotNull
	@Override
	public NoughtsAndCrossesInput deserialiseTypedSingleValue(@NotNull Map<String, Object> json, @NotNull Integer choice) throws DeserialisationException {
		return new NoughtsAndCrossesInput(choice);
	}

	@NotNull
	@Override
	protected Class<Integer> getType() {
		return Integer.class;
	}

	@NotNull
	@Override
	protected String getKeyName() {
		return "choice";
	}
}
