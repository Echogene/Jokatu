package jokatu.game.games.echo.views;

import jokatu.game.games.echo.game.Uzta;
import jokatu.game.player.StandardPlayer;
import jokatu.game.viewresolver.ViewResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;

class UztaViewResolver extends ViewResolver<StandardPlayer, Uzta> {
	UztaViewResolver(Uzta castGame) {
		super(castGame);
	}

	@NotNull
	@Override
	protected ModelAndView getDefaultView() {
		return new ModelAndView("views/uzta_view");
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
