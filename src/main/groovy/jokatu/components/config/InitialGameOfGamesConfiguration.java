package jokatu.components.config;

import jokatu.components.eventhandlers.EventBroadcaster;
import jokatu.game.Game;
import jokatu.game.GameFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static jokatu.game.games.gameofgames.game.GameOfGames.GAME_OF_GAMES;

/**
 * Create the game of games.
 */
@Configuration
public class InitialGameOfGamesConfiguration {

	private final FactoryConfiguration.GameFactories gameFactories;
	private final EventBroadcaster eventBroadcaster;

	public InitialGameOfGamesConfiguration(FactoryConfiguration.GameFactories gameFactories, EventBroadcaster eventBroadcaster) {
		this.gameFactories = gameFactories;
		this.eventBroadcaster = eventBroadcaster;
	}

	@PostConstruct
	public void createGameOfGames() {
		GameFactory factory = gameFactories.getFactory(GAME_OF_GAMES);

		Game<?> game = factory.produceGame("");

		game.observe(event -> eventBroadcaster.broadcast(game, event));

		// Now we are observing events, start the game.
		game.advanceStage();
	}
}
