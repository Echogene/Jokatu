package jokatu.game.games.gameofgames.views;

import jokatu.game.games.gameofgames.game.GameOfGames;
import jokatu.game.player.StandardPlayer;
import jokatu.game.viewresolver.ViewResolver;
import jokatu.game.viewresolver.ViewResolverFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class GameOfGamesViewResolverFactory extends ViewResolverFactory<StandardPlayer, GameOfGames> {
	@NotNull
	@Override
	protected Class<GameOfGames> getGameClass() {
		return GameOfGames.class;
	}

	@NotNull
	@Override
	protected ViewResolver<StandardPlayer, GameOfGames> getResolverFor(@NotNull GameOfGames castGame) {
		return new GameOfGamesViewResolver(castGame);
	}
}
