package jokatu.game.games.uzta.input;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.SingleKeyInputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RandomiseGraphInputDeserialiser extends SingleKeyInputDeserialiser<RandomiseGraphInput> {
	@NotNull
	@Override
	protected RandomiseGraphInput deserialiseSingleValue(@NotNull Map<String, Object> json, @NotNull Object value) throws DeserialisationException {
		return new RandomiseGraphInput();
	}

	@NotNull
	@Override
	protected String getKeyName() {
		return "randomise";
	}
}
