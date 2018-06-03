package jokatu.components.eventhandlers

import jokatu.components.config.FactoryConfiguration.GameFactories
import jokatu.game.Game
import jokatu.game.GameID
import jokatu.game.event.SpecificEventHandler
import jokatu.game.games.gameofgames.event.GameCreatedEvent
import jokatu.game.games.gameofgames.game.GameOfGames
import ophelia.util.MapUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct


/**
 * When a game has been requested to be created by a [GameOfGames], create the game.
 */
@Component
class GameCreatedEventHandler
@Autowired constructor(
		private val gameFactories: GameFactories,
		private val eventBroadcaster: EventBroadcaster
) : SpecificEventHandler<GameCreatedEvent>(GameCreatedEvent::class) {

	// todo: move this map to its own bean
	private val entries = HashMap<GameID, List<GameEntry>>()

	// todo: is it possible to remove this circular dependency?
	@PostConstruct
	fun updateFromCircularDependency() {
		eventBroadcaster.wireEventListeners()
	}

	override fun handleCastEvent(gameOfGames: Game<*>, gameCreatedEvent: GameCreatedEvent) {
		val game = createGame(gameCreatedEvent.gameName, gameCreatedEvent.player.name)

		val id = gameOfGames.identifier
		MapUtils.updateListBasedMap(entries, id, GameEntry(game))

		sender.send(
				"/topic/games.game.$id",
				entries[id]!!
		)
	}

	private fun createGame(gameName: String, playerName: String): Game<*> {
		val factory = gameFactories.getFactory(gameName)

		val game = factory.produceGame(playerName)

		game.observe { event -> eventBroadcaster.broadcast(game, event) }

		// Now we are observing events, start the game.
		game.advanceStage()

		return game
	}

	// This is converted to JSON using Jackson.
	private inner class GameEntry internal constructor(game: Game<*>) {
		val gameId: GameID = game.identifier
		val gameName: String = game.gameName
	}
}
