package jokatu.game.input;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Map;

/**
 * A {@link SingleKeyInputDeserialiser} where we care about the type of the value for the single key.
 * @param <T> the expected type of the single key's value
 * @param <I> the type of the {@link Input} to output
 */
public abstract class TypedSingleKeyInputDeserialiser<T, I extends Input> extends SingleKeyInputDeserialiser<I> {
	@NotNull
	@Override
	protected final I deserialiseSingleValue(@NotNull Map<String, Object> json, @NotNull Object value) throws DeserialisationException {

		Class<T> type = getType();
		if (!type.isInstance(value)) {
			throw new DeserialisationException(
					json,
					MessageFormat.format(
							"The value for ''{0}'' was not a {1}.",
							getKeyName(),
							type.getSimpleName()
					)
			);
		}
		return deserialiseTypedSingleValue(json, type.cast(value));
	}

	@NotNull
	protected abstract I deserialiseTypedSingleValue(@NotNull Map<String, Object> json, @NotNull T value) throws DeserialisationException;

	@NotNull
	protected abstract Class<T> getType();
}
