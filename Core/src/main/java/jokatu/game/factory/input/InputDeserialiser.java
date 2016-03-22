package jokatu.game.factory.input;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.Input;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * This takes JSON from the client and turns it into input.
 * @param <I>
 */
public interface InputDeserialiser<I extends Input> {

	@NotNull
	I deserialise(Map<String, Object> json) throws DeserialisationException;
}
