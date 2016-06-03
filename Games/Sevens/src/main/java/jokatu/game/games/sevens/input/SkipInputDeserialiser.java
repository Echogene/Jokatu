package jokatu.game.games.sevens.input;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.SingleKeyInputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SkipInputDeserialiser extends SingleKeyInputDeserialiser<SkipInput> {
	@NotNull
	@Override
	public SkipInput deserialiseSingleValue(@NotNull Map<String, Object> json, @NotNull Object value) throws DeserialisationException {
		return new SkipInput();
	}

	@NotNull
	@Override
	protected String getKeyName() {
		return "skip";
	}
}
