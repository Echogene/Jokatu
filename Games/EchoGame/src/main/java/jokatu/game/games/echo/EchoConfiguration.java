package jokatu.game.games.echo;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.echo.game.EchoGameFactory;
import jokatu.game.games.echo.game.EchoInputDeserialiser;
import jokatu.game.games.echo.game.EchoPlayerFactory;
import jokatu.game.games.echo.views.EchoViewResolverFactory;
import jokatu.game.input.InputDeserialiser;
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

	private final EchoGameFactory echoGameFactory;
	private final EchoPlayerFactory echoPlayerFactory;
	private final EchoInputDeserialiser echoInputDeserialiser;
	private final EchoViewResolverFactory echoViewResolverFactory;

	@Autowired
	public EchoConfiguration(EchoGameFactory echoGameFactory,
	                         EchoPlayerFactory echoPlayerFactory,
	                         EchoInputDeserialiser echoInputDeserialiser,
	                         EchoViewResolverFactory echoViewResolverFactory
	) {
		this.echoGameFactory = echoGameFactory;
		this.echoPlayerFactory = echoPlayerFactory;
		this.echoInputDeserialiser = echoInputDeserialiser;
		this.echoViewResolverFactory = echoViewResolverFactory;
	}

	@NotNull
	@Override
	public EchoGameFactory getGameFactory() {
		return echoGameFactory;
	}

	@NotNull
	@Override
	public EchoPlayerFactory getPlayerFactory() {
		return echoPlayerFactory;
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
