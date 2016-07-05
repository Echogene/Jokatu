package jokatu.game.games.uzta.views;

import jokatu.game.games.uzta.game.Uzta;
import jokatu.game.player.StandardPlayer;
import jokatu.game.stage.JoiningStage;
import jokatu.game.stage.Stage;
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
		Stage currentStage = game.getCurrentStage();
		String view;
		if (currentStage instanceof JoiningStage) {
			view = "views/game_join";

		} else {
			view = "views/uzta_view";
		}
		return new ModelAndView(view);
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
