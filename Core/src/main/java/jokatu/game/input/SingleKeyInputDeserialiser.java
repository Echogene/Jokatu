package jokatu.game.input;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Map;

/**
 * An {@link InputDeserialiser} where the JSON is expected to have only one key.
 * @param <I>
 */
public abstract class SingleKeyInputDeserialiser<I extends Input> implements InputDeserialiser<I> {
	@NotNull
	@Override
	public final I deserialise(@NotNull Map<String, Object> json) throws DeserialisationException {
		String keyName = getKeyName();
		if (!json.containsKey(keyName)) {
			throw new DeserialisationException(json, MessageFormat.format("Did not contain the key ''{0}''.", keyName));
		}
		Object value = json.get(keyName);
		return deserialiseSingleValue(json, value);
	}

	@NotNull
	protected abstract I deserialiseSingleValue(@NotNull Map<String, Object> json, @NotNull Object value) throws DeserialisationException;

	@NotNull
	protected abstract String getKeyName();
}
