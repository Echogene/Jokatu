package jokatu.components.config

import jokatu.components.eventhandlers.EventBroadcaster
import jokatu.game.games.gameofgames.game.GameOfGames
import jokatu.game.games.gameofgames.game.GameOfGamesFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

/**
 * Create the game of games.
 */
@Configuration
class InitialGameOfGamesConfiguration
@Autowired constructor(
		private val eventBroadcaster: EventBroadcaster,
		private val factory: GameOfGamesFactory
) {
	lateinit var theGameOfGames: GameOfGames

	@PostConstruct
	fun createGameOfGames() {
		theGameOfGames = factory.produceGame("")

		theGameOfGames.observe { event -> eventBroadcaster.broadcast(theGameOfGames, event) }

		// Now we are observing events, start the game.
		theGameOfGames.advanceStage()

		log.debug("{} initialised", InitialGameOfGamesConfiguration::class.java.simpleName)
	}

	companion object {
		private val log = LoggerFactory.getLogger(InitialGameOfGamesConfiguration::class.java)
	}
}
