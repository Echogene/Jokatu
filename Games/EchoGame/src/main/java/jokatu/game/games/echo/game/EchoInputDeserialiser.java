package jokatu.game.games.echo.game;

import jokatu.game.games.echo.input.EchoInput;
import jokatu.game.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EchoInputDeserialiser implements InputDeserialiser<EchoInput> {

	@NotNull
	@Override
	public EchoInput deserialise(@NotNull Map<String, Object> json) {
		return new EchoInput((String) json.get("text"));
	}
}
