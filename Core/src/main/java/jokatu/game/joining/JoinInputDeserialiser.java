package jokatu.game.joining;

import jokatu.game.factory.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class JoinInputDeserialiser implements InputDeserialiser<JoinInput> {
	@NotNull
	@Override
	public JoinInput deserialise(Map<String, Object> json) {
		return new JoinInput();
	}
}
