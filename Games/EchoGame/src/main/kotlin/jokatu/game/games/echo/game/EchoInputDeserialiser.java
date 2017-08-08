package jokatu.game.games.echo.game;

import jokatu.game.games.echo.input.EchoInput;
import jokatu.game.input.TypedSingleKeyInputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EchoInputDeserialiser extends TypedSingleKeyInputDeserialiser<String, EchoInput> {

	@NotNull
	@Override
	public EchoInput deserialiseTypedSingleValue(@NotNull Map<String, Object> json, @NotNull String value) {
		return new EchoInput(value);
	}

	@NotNull
	@Override
	protected Class<String> getType() {
		return String.class;
	}

	@NotNull
	@Override
	protected String getKeyName() {
		return "text";
	}
}
