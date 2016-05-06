package jokatu.game.games.noughtsandcrosses.views;

import jokatu.game.games.noughtsandcrosses.game.NoughtsAndCrossesGame;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.viewresolver.ViewResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author steven
 */
public class NoughtsAndCrossesViewResolver extends ViewResolver<NoughtsAndCrossesPlayer,NoughtsAndCrossesGame> {

	public NoughtsAndCrossesViewResolver(NoughtsAndCrossesGame game) {
		super(game);
	}

	@NotNull
	@Override
	public ModelAndView getDefaultView() {
		return new ModelAndView("views/noughts_and_crosses");
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
