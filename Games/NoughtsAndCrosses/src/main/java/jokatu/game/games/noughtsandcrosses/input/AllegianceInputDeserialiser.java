package jokatu.game.games.noughtsandcrosses.input;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.TypedSingleKeyInputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AllegianceInputDeserialiser extends TypedSingleKeyInputDeserialiser<String, AllegianceInput> {
	@NotNull
	@Override
	public AllegianceInput deserialiseTypedSingleValue(@NotNull Map<String, Object> json, @NotNull String allegiance) throws DeserialisationException {
		NoughtOrCross noughtOrCross = NoughtOrCross.displayValueOf(allegiance);
		return new AllegianceInput(noughtOrCross);
	}

	@NotNull
	@Override
	protected Class<String> getType() {
		return String.class;
	}

	@NotNull
	@Override
	protected String getKeyName() {
		return "allegiance";
	}
}
