package jokatu.game.games.rockpaperscissors;

import jokatu.components.config.GameConfiguration;
import jokatu.game.factory.GameComponent;
import jokatu.game.factory.input.InputDeserialiser;
import jokatu.game.games.rockpaperscissors.game.RockPaperScissorsGameFactory;
import jokatu.game.games.rockpaperscissors.input.RockPaperScissorsInputDeserializer;
import jokatu.game.games.rockpaperscissors.player.RockPaperScissorsPlayerFactory;
import jokatu.game.games.rockpaperscissors.views.RockPaperScissorsViewResolverFactory;
import jokatu.game.joining.JoinInputDeserialiser;
import ophelia.collections.BaseCollection;
import ophelia.collections.pair.UnorderedPair;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import static jokatu.game.games.rockpaperscissors.game.RockPaperScissorsGame.ROCK_PAPER_SCISSORS;

/**
 * @author steven
 */
@GameComponent(gameName = ROCK_PAPER_SCISSORS)
public class RockPaperScissorsConfiguration implements GameConfiguration {

	private final RockPaperScissorsGameFactory factory;
	private final RockPaperScissorsPlayerFactory playerFactory;
	private final RockPaperScissorsInputDeserializer inputDeserializer;
	private final RockPaperScissorsViewResolverFactory viewResolverFactory;
	private final JoinInputDeserialiser joinInputDeserialiser;

	@Autowired
	public RockPaperScissorsConfiguration(
			RockPaperScissorsGameFactory factory,
			RockPaperScissorsPlayerFactory playerFactory,
			RockPaperScissorsInputDeserializer inputDeserializer,
			RockPaperScissorsViewResolverFactory viewResolverFactory,
			JoinInputDeserialiser joinInputDeserialiser) {
		this.factory = factory;
		this.playerFactory = playerFactory;
		this.inputDeserializer = inputDeserializer;
		this.viewResolverFactory = viewResolverFactory;
		this.joinInputDeserialiser = joinInputDeserialiser;
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
	public BaseCollection<? extends InputDeserialiser> getInputDeserialisers() {
		return new UnorderedPair<>(inputDeserializer, joinInputDeserialiser);
	}

	@NotNull
	@Override
	public RockPaperScissorsViewResolverFactory getViewResolverFactory() {
		return viewResolverFactory;
	}
}
