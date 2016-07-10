package jokatu.game.input;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Map;

/**
 * This takes JSON from the client and turns it into input.
 * @param <I> the type of the {@link Input} to output
 */
public abstract class InputDeserialiser<I extends Input> {

	@NotNull
	public abstract I deserialise(@NotNull Map<String, Object> json) throws DeserialisationException;

	protected void getMandatoryKeyValue(@NotNull Map<String, Object> json, @NotNull String keyName) throws DeserialisationException {
		if (!json.containsKey(keyName)) {
			throw new DeserialisationException(json, MessageFormat.format("Did not contain the key ''{0}''.", keyName));
		}
	}
}
