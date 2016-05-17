package jokatu.game.games.gameofgames.views;

import jokatu.game.games.gameofgames.game.GameOfGames;
import jokatu.game.games.gameofgames.player.GameOfGamesPlayer;
import jokatu.game.viewresolver.ViewResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;

class GameOfGamesViewResolver extends ViewResolver<GameOfGamesPlayer, GameOfGames> {
	GameOfGamesViewResolver(GameOfGames game) {
		super(game);
	}

	@NotNull
	@Override
	protected ModelAndView getDefaultView() {
		return new ModelAndView("views/game_admin");
	}

	@NotNull
	@Override
	protected Class<GameOfGamesPlayer> getPlayerClass() {
		return GameOfGamesPlayer.class;
	}

	@NotNull
	@Override
	protected ModelAndView getViewFor(@NotNull GameOfGamesPlayer player) {
		return getDefaultView();
	}
}
