package jokatu.game.games.rockpaperscissors;

import jokatu.game.viewresolver.ViewResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author steven
 */
public class RockPaperScissorsViewResolver extends ViewResolver<RockPaperScissorsPlayer,RockPaperScissorsGame> {

	public RockPaperScissorsViewResolver(RockPaperScissorsGame game) {
		super(game);
	}

	@NotNull
	@Override
	public ModelAndView getDefaultView() {
		return new ModelAndView("views/rock_paper_scissors");
	}

	@NotNull
	@Override
	protected Class<RockPaperScissorsPlayer> handlesPlayer() {
		return RockPaperScissorsPlayer.class;
	}

	@NotNull
	@Override
	protected ModelAndView getViewFor(RockPaperScissorsPlayer player) {
		return getDefaultView();
	}
}
