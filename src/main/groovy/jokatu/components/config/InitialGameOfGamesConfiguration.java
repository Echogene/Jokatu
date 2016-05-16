package jokatu.components.config;

import jokatu.components.eventhandlers.GameCreatedEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static jokatu.game.games.gameofgames.game.GameOfGames.GAME_OF_GAMES;

/**
 * Create the game of games.
 */
@Configuration
public class InitialGameOfGamesConfiguration {

	@Autowired
	private GameCreatedEventHandler gameCreatedEventHandler;

	@PostConstruct
	public void createGameOfGames() {
		gameCreatedEventHandler.createGame(GAME_OF_GAMES, "");
	}
}
