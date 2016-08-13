package jokatu.game.input.acknowledge;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.SingleKeyInputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AcknowledgeInputDeserialiser extends SingleKeyInputDeserialiser<AcknowledgeInput> {
	@NotNull
	@Override
	protected AcknowledgeInput deserialiseSingleValue(
			@NotNull Map<String, Object> json, @NotNull Object value
	) throws DeserialisationException {
		return new AcknowledgeInput();
	}

	@NotNull
	@Override
	protected String getKeyName() {
		return "acknowledge";
	}
}
