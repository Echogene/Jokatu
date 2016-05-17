package jokatu.game.games.rockpaperscissors.views;

import jokatu.game.games.rockpaperscissors.game.RockPaperScissorsGame;
import jokatu.game.games.rockpaperscissors.player.RockPaperScissorsPlayer;
import jokatu.game.viewresolver.ViewResolver;
import jokatu.game.viewresolver.ViewResolverFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * @author steven
 */
@Component
public class RockPaperScissorsViewResolverFactory extends ViewResolverFactory<RockPaperScissorsPlayer,RockPaperScissorsGame> {
	@NotNull
	@Override
	protected Class<RockPaperScissorsGame> getGameClass() {
		return RockPaperScissorsGame.class;
	}

	@NotNull
	@Override
	protected ViewResolver<RockPaperScissorsPlayer, RockPaperScissorsGame> getResolverFor(RockPaperScissorsGame castGame) {
		return new RockPaperScissorsViewResolver(castGame);
	}
}
