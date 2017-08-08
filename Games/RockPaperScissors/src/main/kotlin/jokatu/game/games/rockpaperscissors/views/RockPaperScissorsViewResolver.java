package jokatu.game.games.rockpaperscissors.views;

import jokatu.game.games.rockpaperscissors.game.RockPaperScissorsGame;
import jokatu.game.player.StandardPlayer;
import jokatu.game.stage.JoiningStage;
import jokatu.game.viewresolver.ViewResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author steven
 */
class RockPaperScissorsViewResolver extends ViewResolver<StandardPlayer, RockPaperScissorsGame> {

	RockPaperScissorsViewResolver(RockPaperScissorsGame game) {
		super(game);
	}

	@NotNull
	@Override
	protected ModelAndView getDefaultView() {
		String view;
		if (game.getCurrentStage() instanceof JoiningStage) {
			view = "views/game_join";

		} else {
			view = "views/rock_paper_scissors";
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
