package jokatu.game.joining;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.SingleKeyInputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Deserialises {@link JoinInput}s.  The correct JSON should look like <code>{"join": true}</code>.
 */
@Component
public class JoinInputDeserialiser extends SingleKeyInputDeserialiser<JoinInput> {
	@NotNull
	@Override
	public JoinInput deserialiseSingleValue(@NotNull Map<String, Object> json, @NotNull Object value) throws DeserialisationException {
		return new JoinInput();
	}

	@NotNull
	@Override
	protected String getKeyName() {
		return "join";
	}
}
