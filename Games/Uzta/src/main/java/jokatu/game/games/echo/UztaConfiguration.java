package jokatu.game.games.echo;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.echo.game.UztaFactory;
import jokatu.game.games.echo.views.UztaViewResolverFactory;
import jokatu.game.input.InputDeserialiser;
import jokatu.game.player.PlayerFactory;
import jokatu.game.player.StandardPlayer;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.EmptySet;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import static jokatu.game.games.echo.game.Uzta.UZTA;

/**
 * @author steven
 */
@GameComponent(gameName = UZTA)
public class UztaConfiguration implements GameConfiguration {

	private final UztaFactory uztaFactory;
	private final UztaViewResolverFactory uztaViewResolverFactory;

	@Autowired
	public UztaConfiguration(UztaFactory uztaFactory,
	                         UztaViewResolverFactory uztaViewResolverFactory
	) {
		this.uztaFactory = uztaFactory;
		this.uztaViewResolverFactory = uztaViewResolverFactory;
	}

	@NotNull
	@Override
	public UztaFactory getGameFactory() {
		return uztaFactory;
	}

	@NotNull
	@Override
	public PlayerFactory<?> getPlayerFactory() {
		return (game, username) -> new StandardPlayer(username);
	}

	@NotNull
	@Override
	public BaseCollection<? extends InputDeserialiser> getInputDeserialisers() {
		return EmptySet.emptySet();
	}

	@NotNull
	@Override
	public UztaViewResolverFactory getViewResolverFactory() {
		return uztaViewResolverFactory;
	}
}
