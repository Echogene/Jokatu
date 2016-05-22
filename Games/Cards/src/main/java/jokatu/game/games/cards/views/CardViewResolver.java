package jokatu.game.games.cards.views;

import jokatu.game.games.cards.game.CardGame;
import jokatu.game.games.cards.player.CardPlayer;
import jokatu.game.stage.JoiningStage;
import jokatu.game.viewresolver.ViewResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;

public class CardViewResolver extends ViewResolver<CardPlayer, CardGame> {

	protected CardViewResolver(@NotNull CardGame game) {
		super(game);
	}

	@NotNull
	@Override
	protected ModelAndView getDefaultView() {
		String view;
		if (game.getCurrentStage() instanceof JoiningStage) {
			view = "views/game_join";

		} else {
			view = "views/cards";
		}
		return new ModelAndView(view);
	}

	@NotNull
	@Override
	protected Class<CardPlayer> getPlayerClass() {
		return CardPlayer.class;
	}

	@NotNull
	@Override
	protected ModelAndView getViewFor(@NotNull CardPlayer player) {
		// todo: should an observer have a separate view?
		return getDefaultView();
	}
}
