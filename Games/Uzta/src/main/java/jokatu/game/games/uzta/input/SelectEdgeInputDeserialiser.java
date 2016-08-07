package jokatu.game.games.uzta.input;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SelectEdgeInputDeserialiser extends InputDeserialiser<SelectEdgeInput> {
	@NotNull
	@Override
	public SelectEdgeInput deserialise(@NotNull Map<String, Object> json) throws DeserialisationException {
		Object start = getMandatoryKeyValue("start", json);
		String startId = castValue(String.class, "start", start, json);

		Object end = getMandatoryKeyValue("end", json);
		String endId = castValue(String.class, "end", end, json);

		return new SelectEdgeInput(startId, endId);
	}
}
