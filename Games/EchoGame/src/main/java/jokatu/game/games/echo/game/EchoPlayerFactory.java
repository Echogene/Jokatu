package jokatu.game.games.echo.game;

import jokatu.game.Game;
import jokatu.game.games.echo.player.EchoPlayer;
import jokatu.game.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class EchoPlayerFactory extends PlayerFactory<EchoPlayer> {

	@NotNull
	@Override
	public EchoPlayer produce(@NotNull Game<? extends EchoPlayer> game, @NotNull String username) {
		return new EchoPlayer(username);
	}
}
