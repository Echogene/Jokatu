package jokatu.game.games.echo;

import jokatu.components.config.GameConfiguration;
import jokatu.game.factory.GameComponent;
import jokatu.game.factory.input.InputDeserialiser;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.Singleton;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import static jokatu.game.games.echo.EchoGame.ECHO;

/**
 * @author steven
 */
@GameComponent(gameName = ECHO)
public class EchoConfiguration implements GameConfiguration {

	private final EchoGameFactory echoGameFactory;
	private final EchoViewResolverFactory echoViewResolverFactory;

	@Autowired
	public EchoConfiguration(EchoGameFactory echoGameFactory, EchoViewResolverFactory echoViewResolverFactory) {
		this.echoGameFactory = echoGameFactory;
		this.echoViewResolverFactory = echoViewResolverFactory;
	}

	@NotNull
	@Override
	public EchoGameFactory getGameFactory() {
		return echoGameFactory;
	}

	@NotNull
	@Override
	public EchoGameFactory getPlayerFactory() {
		return echoGameFactory;
	}

	@NotNull
	@Override
	public BaseCollection<? extends InputDeserialiser> getInputDeserialisers() {
		return new Singleton<>(echoGameFactory);
	}

	@NotNull
	@Override
	public EchoViewResolverFactory getViewResolverFactory() {
		return echoViewResolverFactory;
	}
}
