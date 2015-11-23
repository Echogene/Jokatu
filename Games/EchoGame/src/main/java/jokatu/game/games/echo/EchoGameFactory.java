package jokatu.game.games.echo;

import jokatu.game.GameID;
import jokatu.game.factory.GameComponent;
import jokatu.game.factory.game.AbstractGameFactory;
import jokatu.game.factory.input.InputDeserialiser;
import jokatu.game.factory.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;

import static jokatu.game.games.echo.EchoGame.ECHO;

@GameComponent(gameName = ECHO)
public class EchoGameFactory extends AbstractGameFactory
		implements PlayerFactory<EchoPlayer>, InputDeserialiser<EchoInput> {

	@NotNull
	@Override
	protected EchoGame produce(GameID gameID) {
		return new EchoGame(gameID);
	}

	@NotNull
	@Override
	public EchoPlayer produce(@NotNull String username) {
		return new EchoPlayer(username);
	}

	@NotNull
	@Override
	public EchoInput deserialise(String json) {
		return new EchoInput(json);
	}
}
