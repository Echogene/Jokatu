package jokatu.game.games.echo.game;

import jokatu.game.AbstractGameFactory;
import jokatu.game.GameID;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class UztaFactory extends AbstractGameFactory<EchoGame> {

	@NotNull
	@Override
	protected EchoGame produce(@NotNull GameID gameID, @NotNull String creatorName) {
		return new EchoGame(gameID);
	}
}
