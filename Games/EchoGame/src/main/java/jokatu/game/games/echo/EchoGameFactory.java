package jokatu.game.games.echo;

import jokatu.game.GameID;
import jokatu.game.factory.game.AbstractGameFactory;
import jokatu.game.factory.input.InputDeserialiser;
import jokatu.game.factory.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EchoGameFactory extends AbstractGameFactory
		implements PlayerFactory<EchoPlayer>, InputDeserialiser<EchoInput> {

	@NotNull
	@Override
	protected EchoGame produce(@NotNull GameID gameID, @NotNull String creatorName) {
		return new EchoGame(gameID);
	}

	@NotNull
	@Override
	public EchoPlayer produce(@NotNull String username) {
		return new EchoPlayer(username);
	}

	@NotNull
	@Override
	public EchoInput deserialise(Map<String, Object> json) {
		return new EchoInput((String) json.get("text"));
	}
}
