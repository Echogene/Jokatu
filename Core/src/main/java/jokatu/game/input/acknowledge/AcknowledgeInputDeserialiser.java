package jokatu.game.input.acknowledge;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.TypedSingleKeyInputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AcknowledgeInputDeserialiser extends TypedSingleKeyInputDeserialiser<Boolean, AcknowledgeInput> {
	@NotNull
	@Override
	protected AcknowledgeInput deserialiseTypedSingleValue(
			@NotNull Map<String, Object> json, @NotNull Boolean value
	) throws DeserialisationException {
		return new AcknowledgeInput(value);
	}

	@NotNull
	@Override
	protected Class<Boolean> getType() {
		return Boolean.class;
	}

	@NotNull
	@Override
	protected String getKeyName() {
		return "acknowledge";
	}
}
