package jokatu.game.games.noughtsandcrosses;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.noughtsandcrosses.game.NoughtsAndCrossesGame;
import jokatu.game.games.noughtsandcrosses.game.NoughtsAndCrossesGameFactory;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.games.noughtsandcrosses.views.NoughtsAndCrossesViewResolverFactory;
import jokatu.game.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author steven
 */
@GameComponent(gameName = NoughtsAndCrossesGame.NOUGHTS_AND_CROSSES)
public class NoughtsAndCrossesConfiguration implements GameConfiguration {

	private final NoughtsAndCrossesGameFactory factory;
	private final NoughtsAndCrossesViewResolverFactory viewResolverFactory;

	@Autowired
	public NoughtsAndCrossesConfiguration(
			NoughtsAndCrossesGameFactory factory,
			NoughtsAndCrossesViewResolverFactory viewResolverFactory
	) {
		this.factory = factory;
		this.viewResolverFactory = viewResolverFactory;
	}

	@NotNull
	@Override
	public NoughtsAndCrossesGameFactory getGameFactory() {
		return factory;
	}

	@NotNull
	@Override
	public PlayerFactory<?> getPlayerFactory() {
		return (game, username) -> new NoughtsAndCrossesPlayer(username);
	}

	@NotNull
	@Override
	public NoughtsAndCrossesViewResolverFactory getViewResolverFactory() {
		return viewResolverFactory;
	}
}
