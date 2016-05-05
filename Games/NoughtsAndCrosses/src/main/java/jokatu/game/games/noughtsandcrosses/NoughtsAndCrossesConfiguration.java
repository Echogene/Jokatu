package jokatu.game.games.noughtsandcrosses;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.noughtsandcrosses.game.NoughtsAndCrossesGame;
import jokatu.game.games.noughtsandcrosses.game.NoughtsAndCrossesGameFactory;
import jokatu.game.games.noughtsandcrosses.input.NoughtsAndCrossesInputDeserialiser;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayerFactory;
import jokatu.game.games.noughtsandcrosses.views.NoughtsAndCrossesViewResolverFactory;
import jokatu.game.input.InputDeserialiser;
import jokatu.game.joining.JoinInputDeserialiser;
import ophelia.collections.BaseCollection;
import ophelia.collections.pair.UnorderedPair;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author steven
 */
@GameComponent(gameName = NoughtsAndCrossesGame.NOUGHTS_AND_CROSSES)
public class NoughtsAndCrossesConfiguration implements GameConfiguration {

	private final NoughtsAndCrossesGameFactory factory;
	private final NoughtsAndCrossesPlayerFactory playerFactory;
	private final NoughtsAndCrossesInputDeserialiser inputDeserializer;
	private final JoinInputDeserialiser joinInputDeserialiser;
	private final NoughtsAndCrossesViewResolverFactory viewResolverFactory;

	@Autowired
	public NoughtsAndCrossesConfiguration(
			NoughtsAndCrossesGameFactory factory,
			NoughtsAndCrossesPlayerFactory playerFactory,
			NoughtsAndCrossesInputDeserialiser inputDeserializer,
			JoinInputDeserialiser joinInputDeserialiser,
			NoughtsAndCrossesViewResolverFactory viewResolverFactory
	) {
		this.factory = factory;
		this.playerFactory = playerFactory;
		this.inputDeserializer = inputDeserializer;
		this.joinInputDeserialiser = joinInputDeserialiser;
		this.viewResolverFactory = viewResolverFactory;
	}

	@NotNull
	@Override
	public NoughtsAndCrossesGameFactory getGameFactory() {
		return factory;
	}

	@NotNull
	@Override
	public NoughtsAndCrossesPlayerFactory getPlayerFactory() {
		return playerFactory;
	}

	@NotNull
	@Override
	public BaseCollection<? extends InputDeserialiser> getInputDeserialisers() {
		return new UnorderedPair<>(joinInputDeserialiser, inputDeserializer);
	}

	@NotNull
	@Override
	public NoughtsAndCrossesViewResolverFactory getViewResolverFactory() {
		return viewResolverFactory;
	}
}
