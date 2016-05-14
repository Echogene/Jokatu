package jokatu.game.games.noughtsandcrosses.views;

import jokatu.game.games.noughtsandcrosses.game.AllegianceStage;
import jokatu.game.games.noughtsandcrosses.game.NoughtsAndCrossesGame;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.stage.JoiningStage;
import jokatu.game.viewresolver.ViewResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author steven
 */
class NoughtsAndCrossesViewResolver extends ViewResolver<NoughtsAndCrossesPlayer,NoughtsAndCrossesGame> {

	NoughtsAndCrossesViewResolver(NoughtsAndCrossesGame game) {
		super(game);
	}

	@NotNull
	@Override
	public ModelAndView getDefaultView() {
		String view;
		if (game.getCurrentStage() instanceof JoiningStage) {
			view = "views/game_join";

		} else if (game.getCurrentStage() instanceof AllegianceStage) {
			view = "views/allegiance";

		} else {
			view = "views/noughts_and_crosses";
		}
		return new ModelAndView(view);
	}

	@NotNull
	@Override
	protected Class<NoughtsAndCrossesPlayer> handlesPlayer() {
		return NoughtsAndCrossesPlayer.class;
	}

	@NotNull
	@Override
	protected ModelAndView getViewFor(NoughtsAndCrossesPlayer player) {
		return getDefaultView();
	}
}
