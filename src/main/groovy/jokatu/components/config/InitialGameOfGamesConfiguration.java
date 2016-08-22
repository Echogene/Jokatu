package jokatu.components.config;

import jokatu.components.eventhandlers.EventBroadcaster;
import jokatu.game.games.gameofgames.game.GameOfGames;
import jokatu.game.games.gameofgames.game.GameOfGamesFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Create the game of games.
 */
@Configuration
public class InitialGameOfGamesConfiguration {

	private final GameOfGamesFactory factory;

	private final EventBroadcaster eventBroadcaster;

	private GameOfGames theGameOfGames;

	@Autowired
	public InitialGameOfGamesConfiguration(EventBroadcaster eventBroadcaster, GameOfGamesFactory factory) {
		this.eventBroadcaster = eventBroadcaster;
		this.factory = factory;
	}

	@PostConstruct
	public void createGameOfGames() {
		theGameOfGames = factory.produceGame("");

		theGameOfGames.observe(event -> eventBroadcaster.broadcast(theGameOfGames, event));

		// Now we are observing events, start the game.
		theGameOfGames.advanceStage();
	}

	public GameOfGames getTheGameOfGames() {
		return theGameOfGames;
	}
}
