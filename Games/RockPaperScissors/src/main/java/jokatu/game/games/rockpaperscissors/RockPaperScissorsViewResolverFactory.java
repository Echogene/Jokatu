package jokatu.game.games.rockpaperscissors;

import jokatu.game.factory.GameComponent;
import jokatu.game.viewresolver.ViewResolver;
import jokatu.game.viewresolver.ViewResolverFactory;
import org.jetbrains.annotations.NotNull;

import static jokatu.game.games.rockpaperscissors.RockPaperScissorsGame.ROCK_PAPER_SCISSORS;

/**
 * @author steven
 */
@GameComponent(gameName = ROCK_PAPER_SCISSORS)
public class RockPaperScissorsViewResolverFactory extends ViewResolverFactory<RockPaperScissorsPlayer,RockPaperScissorsGame> {
	@NotNull
	@Override
	protected Class<RockPaperScissorsGame> handlesGame() {
		return RockPaperScissorsGame.class;
	}

	@NotNull
	@Override
	protected ViewResolver<RockPaperScissorsPlayer, RockPaperScissorsGame> getResolverFor(RockPaperScissorsGame castGame) {
		return new RockPaperScissorsViewResolver(castGame);
	}
}
