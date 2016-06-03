package jokatu.game.games.gameofgames.input;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.TypedSingleKeyInputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CreateGameInputDeserialiser extends TypedSingleKeyInputDeserialiser<String, CreateGameInput> {
	@NotNull
	@Override
	public CreateGameInput deserialiseTypedSingleValue(@NotNull Map<String, Object> json, @NotNull String gameName) throws DeserialisationException {
		return new CreateGameInput(gameName);
	}

	@NotNull
	@Override
	protected Class<String> getType() {
		return String.class;
	}

	@NotNull
	@Override
	protected String getKeyName() {
		return "gameName";
	}
}
