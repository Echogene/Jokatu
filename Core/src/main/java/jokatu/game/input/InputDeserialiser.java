package jokatu.game.input;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * This takes JSON from the client and turns it into input.
 * @param <I>
 */
public interface InputDeserialiser<I extends Input> {

	@NotNull
	I deserialise(@NotNull Map<String, Object> json) throws DeserialisationException;
}
