package jokatu.game.games.sevens.views;

import jokatu.game.games.sevens.game.SevensGame;
import jokatu.game.games.sevens.player.SevensPlayer;
import jokatu.game.stage.MinAndMaxJoiningStage;
import jokatu.game.viewresolver.ViewResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;

class SevensViewResolver extends ViewResolver<SevensPlayer, SevensGame> {

	SevensViewResolver(@NotNull SevensGame game) {
		super(game);
	}

	@NotNull
	@Override
	protected ModelAndView getDefaultView() {
		String view;
		if (game.getCurrentStage() instanceof MinAndMaxJoiningStage) {
			view = "views/game_join_with_start";

		} else {
			view = "views/sevens";
		}
		return new ModelAndView(view);
	}

	@NotNull
	@Override
	protected Class<SevensPlayer> getPlayerClass() {
		return SevensPlayer.class;
	}

	@NotNull
	@Override
	protected ModelAndView getViewFor(@NotNull SevensPlayer player) {
		// todo: should an observer have a separate view?
		return getDefaultView();
	}
}
