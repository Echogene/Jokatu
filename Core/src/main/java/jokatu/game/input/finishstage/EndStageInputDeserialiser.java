package jokatu.game.input.finishstage;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.SingleKeyInputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Deserialises {@link EndStageInput}s.  The correct JSON should look like <code>{"end": true}</code>.
 */
@Component
public class EndStageInputDeserialiser extends SingleKeyInputDeserialiser<EndStageInput> {
	@NotNull
	@Override
	public EndStageInput deserialiseSingleValue(@NotNull Map<String, Object> json, @NotNull Object value) throws DeserialisationException {
		return new EndStageInput();
	}

	@NotNull
	@Override
	protected String getKeyName() {
		return "end";
	}
}
