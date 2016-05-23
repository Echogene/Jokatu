package jokatu.game.games.gameofgames.views;

import jokatu.game.games.gameofgames.game.GameOfGames;
import jokatu.game.player.StandardPlayer;
import jokatu.game.viewresolver.ViewResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;

class GameOfGamesViewResolver extends ViewResolver<StandardPlayer, GameOfGames> {
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
	protected Class<StandardPlayer> getPlayerClass() {
		return StandardPlayer.class;
	}

	@NotNull
	@Override
	protected ModelAndView getViewFor(@NotNull StandardPlayer player) {
		return getDefaultView();
	}
}
