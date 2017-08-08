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
		if (json.size() > 1) {
			throw new DeserialisationException(json, "There was more than one key.");
		}
		Object value = getMandatoryKeyValue(getKeyName(), json);
		return deserialiseSingleValue(json, value);
	}

	@NotNull
	protected abstract I deserialiseSingleValue(@NotNull Map<String, Object> json, @NotNull Object value) throws DeserialisationException;

	@NotNull
	protected abstract String getKeyName();
}
