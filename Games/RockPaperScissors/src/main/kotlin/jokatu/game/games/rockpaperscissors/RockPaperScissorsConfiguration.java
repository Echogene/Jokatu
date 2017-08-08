package jokatu.game.games.rockpaperscissors;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.rockpaperscissors.game.RockPaperScissorsGameFactory;
import jokatu.game.games.rockpaperscissors.views.RockPaperScissorsViewResolverFactory;
import jokatu.game.player.PlayerFactory;
import jokatu.game.player.StandardPlayer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import static jokatu.game.games.rockpaperscissors.game.RockPaperScissorsGame.ROCK_PAPER_SCISSORS;

/**
 * @author steven
 */
@GameComponent(gameName = ROCK_PAPER_SCISSORS)
public class RockPaperScissorsConfiguration implements GameConfiguration {

	private final RockPaperScissorsGameFactory factory;
	private final RockPaperScissorsViewResolverFactory viewResolverFactory;

	@Autowired
	public RockPaperScissorsConfiguration(
			RockPaperScissorsGameFactory factory,
			RockPaperScissorsViewResolverFactory viewResolverFactory
	) {
		this.factory = factory;
		this.viewResolverFactory = viewResolverFactory;
	}

	@NotNull
	@Override
	public RockPaperScissorsGameFactory getGameFactory() {
		return factory;
	}

	@NotNull
	@Override
	public PlayerFactory<?> getPlayerFactory() {
		return ((game, username) -> new StandardPlayer(username));
	}

	@NotNull
	@Override
	public RockPaperScissorsViewResolverFactory getViewResolverFactory() {
		return viewResolverFactory;
	}
}
