package jokatu.game.joining.finish;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.SingleKeyInputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FinishJoiningInputDeserialiser extends SingleKeyInputDeserialiser<FinishJoiningInput> {
	@NotNull
	@Override
	public FinishJoiningInput deserialiseSingleValue(@NotNull Map<String, Object> json, @NotNull Object value) throws DeserialisationException {
		return new FinishJoiningInput();
	}

	@NotNull
	@Override
	protected String getKeyName() {
		return "start";
	}
}
