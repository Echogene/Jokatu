package jokatu.game.games.gameofgames;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.gameofgames.game.GameOfGames;
import jokatu.game.games.gameofgames.game.GameOfGamesFactory;
import jokatu.game.games.gameofgames.input.CreateGameInputDeserialiser;
import jokatu.game.games.gameofgames.player.GameOfGamesPlayer;
import jokatu.game.games.gameofgames.views.GameOfGamesViewResolverFactory;
import jokatu.game.input.InputDeserialiser;
import jokatu.game.player.PlayerFactory;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.Singleton;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author steven
 */
@GameComponent(gameName = GameOfGames.GAME_OF_GAMES)
public class GameOfGamesConfiguration implements GameConfiguration {

	private final GameOfGamesFactory factory;
	private final CreateGameInputDeserialiser createGameInputDeserialiser;
	private final GameOfGamesViewResolverFactory viewResolverFactory;

	@Autowired
	public GameOfGamesConfiguration(
			GameOfGamesFactory factory,
			CreateGameInputDeserialiser createGameInputDeserialiser,
			GameOfGamesViewResolverFactory viewResolverFactory
	) {
		this.factory = factory;
		this.createGameInputDeserialiser = createGameInputDeserialiser;
		this.viewResolverFactory = viewResolverFactory;
	}

	@NotNull
	@Override
	public GameOfGamesFactory getGameFactory() {
		return factory;
	}

	@NotNull
	@Override
	public PlayerFactory getPlayerFactory() {
		return ((game, username) -> new GameOfGamesPlayer(username));
	}

	@NotNull
	@Override
	public BaseCollection<? extends InputDeserialiser> getInputDeserialisers() {
		return new Singleton<>(createGameInputDeserialiser);
	}

	@NotNull
	@Override
	public GameOfGamesViewResolverFactory getViewResolverFactory() {
		return viewResolverFactory;
	}
}
