package jokatu.game.games.uzta;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.uzta.game.UztaFactory;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.games.uzta.views.UztaViewResolverFactory;
import jokatu.game.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import static jokatu.game.games.uzta.game.Uzta.UZTA;

/**
 * @author steven
 */
@GameComponent(gameName = UZTA)
public class UztaConfiguration implements GameConfiguration {

	private final UztaFactory uztaFactory;
	private final UztaViewResolverFactory uztaViewResolverFactory;

	@Autowired
	public UztaConfiguration(UztaFactory uztaFactory,
	                         UztaViewResolverFactory uztaViewResolverFactory) {
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
		return (game, username) -> new UztaPlayer(username);
	}

	@NotNull
	@Override
	public UztaViewResolverFactory getViewResolverFactory() {
		return uztaViewResolverFactory;
	}
}
