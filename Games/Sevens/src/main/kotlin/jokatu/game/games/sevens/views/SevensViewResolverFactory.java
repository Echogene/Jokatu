package jokatu.game.games.sevens.views;

import jokatu.game.games.sevens.game.SevensGame;
import jokatu.game.games.sevens.player.SevensPlayer;
import jokatu.game.viewresolver.ViewResolver;
import jokatu.game.viewresolver.ViewResolverFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * @author steven
 */
@Component
public class SevensViewResolverFactory extends ViewResolverFactory<SevensPlayer, SevensGame> {
	@NotNull
	@Override
	protected Class<SevensGame> getGameClass() {
		return SevensGame.class;
	}

	@NotNull
	@Override
	protected ViewResolver<SevensPlayer, SevensGame> getResolverFor(@NotNull SevensGame castGame) {
		return new SevensViewResolver(castGame);
	}
}
