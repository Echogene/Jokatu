package jokatu.game.factory.input;

import jokatu.game.input.Input;
import org.jetbrains.annotations.NotNull;

public interface InputDeserialiser<I extends Input> {

	@NotNull
	I deserialise(String json);
}
