package jokatu.game.games.echo;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.echo.game.UztaFactory;
import jokatu.game.games.echo.game.EchoInputDeserialiser;
import jokatu.game.player.StandardPlayer;
import jokatu.game.games.echo.views.EchoViewResolverFactory;
import jokatu.game.input.InputDeserialiser;
import jokatu.game.player.PlayerFactory;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.Singleton;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import static jokatu.game.games.echo.game.EchoGame.ECHO;

/**
 * @author steven
 */
@GameComponent(gameName = ECHO)
public class EchoConfiguration implements GameConfiguration {

	private final UztaFactory echoGameFactory;
	private final EchoInputDeserialiser echoInputDeserialiser;
	private final EchoViewResolverFactory echoViewResolverFactory;

	@Autowired
	public EchoConfiguration(UztaFactory echoGameFactory,
	                         EchoInputDeserialiser echoInputDeserialiser,
	                         EchoViewResolverFactory echoViewResolverFactory
	) {
		this.echoGameFactory = echoGameFactory;
		this.echoInputDeserialiser = echoInputDeserialiser;
		this.echoViewResolverFactory = echoViewResolverFactory;
	}

	@NotNull
	@Override
	public UztaFactory getGameFactory() {
		return echoGameFactory;
	}

	@NotNull
	@Override
	public PlayerFactory<?> getPlayerFactory() {
		return (game, username) -> new StandardPlayer(username);
	}

	@NotNull
	@Override
	public BaseCollection<? extends InputDeserialiser> getInputDeserialisers() {
		return new Singleton<>(echoInputDeserialiser);
	}

	@NotNull
	@Override
	public EchoViewResolverFactory getViewResolverFactory() {
		return echoViewResolverFactory;
	}
}
