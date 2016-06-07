package jokatu.game.games.noughtsandcrosses;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.noughtsandcrosses.game.NoughtsAndCrossesGame;
import jokatu.game.games.noughtsandcrosses.game.NoughtsAndCrossesGameFactory;
import jokatu.game.games.noughtsandcrosses.input.AllegianceInputDeserialiser;
import jokatu.game.games.noughtsandcrosses.input.NoughtsAndCrossesInputDeserialiser;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.games.noughtsandcrosses.views.NoughtsAndCrossesViewResolverFactory;
import jokatu.game.input.InputDeserialiser;
import jokatu.game.joining.JoinInputDeserialiser;
import jokatu.game.player.PlayerFactory;
import ophelia.collections.BaseCollection;
import ophelia.collections.list.StandardList;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * @author steven
 */
@GameComponent(gameName = NoughtsAndCrossesGame.NOUGHTS_AND_CROSSES)
public class NoughtsAndCrossesConfiguration implements GameConfiguration {

	private final NoughtsAndCrossesGameFactory factory;
	private final JoinInputDeserialiser joinInputDeserialiser;
	private final AllegianceInputDeserialiser allegianceInputDeserialiser;
	private final NoughtsAndCrossesInputDeserialiser inputDeserializer;
	private final NoughtsAndCrossesViewResolverFactory viewResolverFactory;

	@Autowired
	public NoughtsAndCrossesConfiguration(
			NoughtsAndCrossesGameFactory factory,
			JoinInputDeserialiser joinInputDeserialiser,
			AllegianceInputDeserialiser allegianceInputDeserialiser,
			NoughtsAndCrossesInputDeserialiser inputDeserializer,
			NoughtsAndCrossesViewResolverFactory viewResolverFactory
	) {
		this.factory = factory;
		this.joinInputDeserialiser = joinInputDeserialiser;
		this.allegianceInputDeserialiser = allegianceInputDeserialiser;
		this.inputDeserializer = inputDeserializer;
		this.viewResolverFactory = viewResolverFactory;
	}

	@NotNull
	@Override
	public NoughtsAndCrossesGameFactory getGameFactory() {
		return factory;
	}

	@NotNull
	@Override
	public PlayerFactory<?> getPlayerFactory() {
		return (game, username) -> new NoughtsAndCrossesPlayer(username);
	}

	@NotNull
	@Override
	public BaseCollection<? extends InputDeserialiser> getInputDeserialisers() {
		return new StandardList<>(Arrays.asList(joinInputDeserialiser, allegianceInputDeserialiser, inputDeserializer));
	}

	@NotNull
	@Override
	public NoughtsAndCrossesViewResolverFactory getViewResolverFactory() {
		return viewResolverFactory;
	}
}
