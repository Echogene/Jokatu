package jokatu.game.games.uzta.views;

import jokatu.game.games.uzta.game.Uzta;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.viewresolver.ViewResolver;
import jokatu.game.viewresolver.ViewResolverFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * @author steven
 */
@Component
public class UztaViewResolverFactory extends ViewResolverFactory<UztaPlayer, Uzta> {
	@NotNull
	@Override
	protected Class<Uzta> getGameClass() {
		return Uzta.class;
	}

	@NotNull
	@Override
	protected ViewResolver<UztaPlayer, Uzta> getResolverFor(@NotNull Uzta castGame) {
		return new UztaViewResolver(castGame);
	}
}
