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

	@NotNull
	protected Object getMandatoryKeyValue(@NotNull String keyName, @NotNull Map<String, Object> json) throws DeserialisationException {
		if (!json.containsKey(keyName)) {
			throw new DeserialisationException(json, MessageFormat.format("Did not contain the key ''{0}''.", keyName));
		}
		Object value = json.get(keyName);
		if (value == null) {
			throw new DeserialisationException(json, MessageFormat.format("The value for the key ''{0}'' was null.", keyName));
		}
		return value;
	}

	@NotNull
	protected <T> T castValue(
			@NotNull Class<T> type,
			@NotNull String keyName,
			@NotNull Object value,
			@NotNull Map<String, Object> json
	) throws DeserialisationException {
		if (!type.isInstance(value)) {
			throw new DeserialisationException(
					json,
					MessageFormat.format(
							"The value for ''{0}'' was not a {1}.",
							keyName,
							type.getSimpleName()
					)
			);
		}
		return type.cast(value);
	}
}
