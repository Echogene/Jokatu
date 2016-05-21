package jokatu.game.games.cards.views;

import jokatu.game.games.cards.game.CardGame;
import jokatu.game.games.cards.player.CardPlayer;
import jokatu.game.viewresolver.ViewResolver;
import jokatu.game.viewresolver.ViewResolverFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author steven
 */
@Component
public class CardViewResolverFactory extends ViewResolverFactory<CardPlayer, CardGame> {
	@NotNull
	@Override
	protected Class<CardGame> getGameClass() {
		return CardGame.class;
	}

	@NotNull
	@Override
	protected ViewResolver<CardPlayer, CardGame> getResolverFor(@NotNull CardGame castGame) {
		return new ViewResolver<CardPlayer, CardGame>(castGame) {
			@NotNull
			@Override
			protected ModelAndView getDefaultView() {
				return new ModelAndView("views/cards");
			}

			@NotNull
			@Override
			protected Class<CardPlayer> getPlayerClass() {
				return CardPlayer.class;
			}

			@NotNull
			@Override
			protected ModelAndView getViewFor(@NotNull CardPlayer player) {
				return getDefaultView();
			}
		};
	}
}
