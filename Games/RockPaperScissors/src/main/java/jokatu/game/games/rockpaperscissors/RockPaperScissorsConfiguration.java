package jokatu.game.games.rockpaperscissors;

import jokatu.components.config.GameConfiguration;
import jokatu.game.factory.GameComponent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import static jokatu.game.games.rockpaperscissors.RockPaperScissorsGame.ROCK_PAPER_SCISSORS;

/**
 * @author steven
 */
@GameComponent(gameName = ROCK_PAPER_SCISSORS)
public class RockPaperScissorsConfiguration implements GameConfiguration {

	private final RockPaperScissorsGameFactory factory;
	private final RockPaperScissorsPlayerFactory playerFactory;
	private final RockPaperScissorsInputDeserializer inputDeserializer;
	private final RockPaperScissorsViewResolverFactory viewResolverFactory;

	@Autowired
	public RockPaperScissorsConfiguration(
			RockPaperScissorsGameFactory factory,
			RockPaperScissorsPlayerFactory playerFactory,
			RockPaperScissorsInputDeserializer inputDeserializer,
			RockPaperScissorsViewResolverFactory viewResolverFactory
	) {
		this.factory = factory;
		this.playerFactory = playerFactory;
		this.inputDeserializer = inputDeserializer;
		this.viewResolverFactory = viewResolverFactory;
	}

	@NotNull
	@Override
	public RockPaperScissorsGameFactory getGameFactory() {
		return factory;
	}

	@NotNull
	@Override
	public RockPaperScissorsPlayerFactory getPlayerFactory() {
		return playerFactory;
	}

	@NotNull
	@Override
	public RockPaperScissorsInputDeserializer getInputDeserialiser() {
		return inputDeserializer;
	}

	@NotNull
	@Override
	public RockPaperScissorsViewResolverFactory getViewResolverFactory() {
		return viewResolverFactory;
	}
}
