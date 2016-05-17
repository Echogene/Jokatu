package jokatu.game.games.noughtsandcrosses.views;

import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.games.noughtsandcrosses.game.NoughtsAndCrossesGame;
import jokatu.game.viewresolver.ViewResolver;
import jokatu.game.viewresolver.ViewResolverFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * @author steven
 */
@Component
public class NoughtsAndCrossesViewResolverFactory extends ViewResolverFactory<NoughtsAndCrossesPlayer,NoughtsAndCrossesGame> {
	@NotNull
	@Override
	protected Class<NoughtsAndCrossesGame> getGameClass() {
		return NoughtsAndCrossesGame.class;
	}

	@NotNull
	@Override
	protected ViewResolver<NoughtsAndCrossesPlayer, NoughtsAndCrossesGame> getResolverFor(NoughtsAndCrossesGame castGame) {
		return new NoughtsAndCrossesViewResolver(castGame);
	}
}
