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
		return new AllegianceInput(NoughtOrCross.valueOf((String) json.get("allegiance")));
	}
}
