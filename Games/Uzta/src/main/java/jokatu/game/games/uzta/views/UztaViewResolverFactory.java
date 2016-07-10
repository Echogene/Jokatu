package jokatu.game.games.uzta.views;

import jokatu.game.games.uzta.game.Uzta;
import jokatu.game.player.StandardPlayer;
import jokatu.game.viewresolver.ViewResolver;
import jokatu.game.viewresolver.ViewResolverFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * @author steven
 */
@Component
public class UztaViewResolverFactory extends ViewResolverFactory<StandardPlayer, Uzta> {
	@NotNull
	@Override
	protected Class<Uzta> getGameClass() {
		return Uzta.class;
	}

	@NotNull
	@Override
	protected ViewResolver<StandardPlayer, Uzta> getResolverFor(@NotNull Uzta castGame) {
		return new UztaViewResolver(castGame);
	}
}
