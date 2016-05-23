package jokatu.game.games.rockpaperscissors;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.rockpaperscissors.game.RockPaperScissorsGameFactory;
import jokatu.game.games.rockpaperscissors.input.RockPaperScissorsInputDeserializer;
import jokatu.game.games.rockpaperscissors.player.RockPaperScissorsPlayer;
import jokatu.game.games.rockpaperscissors.views.RockPaperScissorsViewResolverFactory;
import jokatu.game.input.InputDeserialiser;
import jokatu.game.joining.JoinInputDeserialiser;
import jokatu.game.player.PlayerFactory;
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
	private final RockPaperScissorsInputDeserializer inputDeserializer;
	private final RockPaperScissorsViewResolverFactory viewResolverFactory;
	private final JoinInputDeserialiser joinInputDeserialiser;

	@Autowired
	public RockPaperScissorsConfiguration(
			RockPaperScissorsGameFactory factory,
			RockPaperScissorsInputDeserializer inputDeserializer,
			RockPaperScissorsViewResolverFactory viewResolverFactory,
			JoinInputDeserialiser joinInputDeserialiser) {
		this.factory = factory;
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
	public PlayerFactory getPlayerFactory() {
		return ((game, username) -> new RockPaperScissorsPlayer(username));
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
