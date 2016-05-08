package jokatu.game.games.noughtsandcrosses.input;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AllegianceInputDeserialiser implements InputDeserialiser<AllegianceInput> {
	@NotNull
	@Override
	public AllegianceInput deserialise(Map<String, Object> json) throws DeserialisationException {
		if (!json.containsKey("allegiance")) {
			throw new DeserialisationException(json, "Did not contain the key 'allegiance'.");
		}
		Object value = json.get("allegiance");
		if (!(value instanceof String)) {
			throw new DeserialisationException(json, "The value for 'allegiance' was not a string.");
		}
		String allegiance = (String) value;
		NoughtOrCross noughtOrCross = NoughtOrCross.displayValueOf(allegiance);
		return new AllegianceInput(noughtOrCross);
	}
}
