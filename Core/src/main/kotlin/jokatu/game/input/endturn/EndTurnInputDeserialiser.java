package jokatu.game.input.endturn;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.SingleKeyInputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EndTurnInputDeserialiser extends SingleKeyInputDeserialiser<EndTurnInput> {
	@NotNull
	@Override
	public EndTurnInput deserialiseSingleValue(@NotNull Map<String, Object> json, @NotNull Object value) throws DeserialisationException {
		return new EndTurnInput();
	}

	@NotNull
	@Override
	protected String getKeyName() {
		return "skip";
	}
}
