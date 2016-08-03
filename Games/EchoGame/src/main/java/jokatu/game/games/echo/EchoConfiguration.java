package jokatu.game.games.echo;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.echo.game.EchoFactory;
import jokatu.game.games.echo.views.EchoViewResolverFactory;
import jokatu.game.player.PlayerFactory;
import jokatu.game.player.StandardPlayer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import static jokatu.game.games.echo.game.EchoGame.ECHO;

/**
 * @author steven
 */
@GameComponent(gameName = ECHO)
public class EchoConfiguration implements GameConfiguration {

	private final EchoFactory echoGameFactory;
	private final EchoViewResolverFactory echoViewResolverFactory;

	@Autowired
	public EchoConfiguration(EchoFactory echoGameFactory,
	                         EchoViewResolverFactory echoViewResolverFactory
	) {
		this.echoGameFactory = echoGameFactory;
		this.echoViewResolverFactory = echoViewResolverFactory;
	}

	@NotNull
	@Override
	public EchoFactory getGameFactory() {
		return echoGameFactory;
	}

	@NotNull
	@Override
	public PlayerFactory<?> getPlayerFactory() {
		return (game, username) -> new StandardPlayer(username);
	}

	@NotNull
	@Override
	public EchoViewResolverFactory getViewResolverFactory() {
		return echoViewResolverFactory;
	}
}
