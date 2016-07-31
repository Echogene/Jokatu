package jokatu.game.games.uzta.views;

import jokatu.game.games.uzta.game.FirstPlacementStage;
import jokatu.game.games.uzta.game.SetupStage;
import jokatu.game.games.uzta.game.Uzta;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.stage.MinAndMaxJoiningStage;
import jokatu.game.stage.Stage;
import jokatu.game.viewresolver.ViewResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;

class UztaViewResolver extends ViewResolver<UztaPlayer, Uzta> {
	UztaViewResolver(Uzta castGame) {
		super(castGame);
	}

	@NotNull
	@Override
	protected ModelAndView getDefaultView() {
		Stage currentStage = game.getCurrentStage();
		String view;
		if (currentStage instanceof MinAndMaxJoiningStage) {
			view = "views/game_join_with_start";

		} else if (currentStage instanceof SetupStage) {
			view = "views/uzta_setup";

		} else if (currentStage instanceof FirstPlacementStage) {
			view = "views/uzta";

		} else {
			view = "views/uzta";
		}
		return new ModelAndView(view);
	}

	@NotNull
	@Override
	protected Class<UztaPlayer> getPlayerClass() {
		return UztaPlayer.class;
	}

	@NotNull
	@Override
	protected ModelAndView getViewFor(@NotNull UztaPlayer player) {
		return getDefaultView();
	}
}
