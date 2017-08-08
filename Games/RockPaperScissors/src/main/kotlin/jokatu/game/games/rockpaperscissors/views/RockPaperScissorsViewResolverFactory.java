package jokatu.game.games.rockpaperscissors.views;

import jokatu.game.games.rockpaperscissors.game.RockPaperScissorsGame;
import jokatu.game.player.StandardPlayer;
import jokatu.game.viewresolver.ViewResolver;
import jokatu.game.viewresolver.ViewResolverFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * @author steven
 */
@Component
public class RockPaperScissorsViewResolverFactory extends ViewResolverFactory<StandardPlayer, RockPaperScissorsGame> {
	@NotNull
	@Override
	protected Class<RockPaperScissorsGame> getGameClass() {
		return RockPaperScissorsGame.class;
	}

	@NotNull
	@Override
	protected ViewResolver<StandardPlayer, RockPaperScissorsGame> getResolverFor(@NotNull RockPaperScissorsGame castGame) {
		return new RockPaperScissorsViewResolver(castGame);
	}
}
