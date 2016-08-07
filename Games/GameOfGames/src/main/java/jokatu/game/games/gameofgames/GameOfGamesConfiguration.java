package jokatu.game.games.gameofgames;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.gameofgames.game.GameOfGames;
import jokatu.game.games.gameofgames.game.GameOfGamesFactory;
import jokatu.game.games.gameofgames.views.GameOfGamesViewResolverFactory;
import jokatu.game.player.PlayerFactory;
import jokatu.game.player.StandardPlayer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author steven
 */
@GameComponent(gameName = GameOfGames.GAME_OF_GAMES)
public class GameOfGamesConfiguration implements GameConfiguration {

	private final GameOfGamesFactory factory;
	private final GameOfGamesViewResolverFactory viewResolverFactory;

	@Autowired
	public GameOfGamesConfiguration(
			GameOfGamesFactory factory,
			GameOfGamesViewResolverFactory viewResolverFactory
	) {
		this.factory = factory;
		this.viewResolverFactory = viewResolverFactory;
	}

	@NotNull
	@Override
	public GameOfGamesFactory getGameFactory() {
		return factory;
	}

	@NotNull
	@Override
	public PlayerFactory<?> getPlayerFactory() {
		return ((game, username) -> new StandardPlayer(username));
	}

	@NotNull
	@Override
	public GameOfGamesViewResolverFactory getViewResolverFactory() {
		return viewResolverFactory;
	}
}
