package jokatu.game.games.cards.views;

import jokatu.game.games.cards.game.CardGame;
import jokatu.game.games.cards.player.CardPlayer;
import jokatu.game.viewresolver.ViewResolver;
import jokatu.game.viewresolver.ViewResolverFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

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
		return new CardViewResolver(castGame);
	}
}
