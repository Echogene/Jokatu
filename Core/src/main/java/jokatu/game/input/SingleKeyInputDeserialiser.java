package jokatu.game.input;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * An {@link InputDeserialiser} where the JSON is expected to have only one key.
 * @param <I> the type of the {@link Input} to output
 */
public abstract class SingleKeyInputDeserialiser<I extends Input> extends InputDeserialiser<I> {
	@NotNull
	@Override
	public final I deserialise(@NotNull Map<String, Object> json) throws DeserialisationException {
		String keyName = getKeyName();
		getMandatoryKeyValue(json, keyName);
		Object value = json.get(keyName);
		return deserialiseSingleValue(json, value);
	}

	@NotNull
	protected abstract I deserialiseSingleValue(@NotNull Map<String, Object> json, @NotNull Object value) throws DeserialisationException;

	@NotNull
	protected abstract String getKeyName();
}
