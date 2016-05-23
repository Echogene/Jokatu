package jokatu.game.games.echo.game;

import jokatu.game.games.echo.player.EchoPlayer;
import jokatu.game.player.AbstractPlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class EchoPlayerFactory extends AbstractPlayerFactory<EchoPlayer, EchoGame> {

	@NotNull
	@Override
	protected Class<EchoGame> getGameClass() {
		return EchoGame.class;
	}

	@NotNull
	@Override
	protected EchoPlayer produceInCastGame(@NotNull EchoGame game, @NotNull String username) {
		return new EchoPlayer(username);
	}
}
