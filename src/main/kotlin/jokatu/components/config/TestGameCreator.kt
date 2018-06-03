package jokatu.components.config

import jokatu.components.dao.GameDao
import jokatu.components.eventhandlers.GameCreatedEventHandler
import jokatu.game.GameID
import jokatu.game.exception.GameException
import jokatu.game.games.gameofgames.event.GameCreatedEvent
import jokatu.game.games.gameofgames.game.GameOfGames
import jokatu.game.games.uzta.game.FirstPlacementStage
import jokatu.game.games.uzta.game.Uzta
import jokatu.game.games.uzta.game.Uzta.Companion.UZTA
import jokatu.game.games.uzta.graph.LineSegment
import jokatu.game.games.uzta.input.SelectEdgeInput
import jokatu.game.games.uzta.player.UztaPlayer
import jokatu.game.input.finishstage.EndStageInput
import jokatu.game.joining.JoinInput
import jokatu.game.player.StandardPlayer
import org.slf4j.getLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import java.util.stream.Collectors.toList
import javax.annotation.PostConstruct

/**
 * Create some games at startup for testing.
 */
@Configuration
class TestGameCreator
@Autowired constructor(
		configuration: InitialGameOfGamesConfiguration,
		private val gameCreatedEventHandler: GameCreatedEventHandler,
		private val gameDao: GameDao
) {
	private val theGameOfGames: GameOfGames = configuration.theGameOfGames

	@PostConstruct
	@Throws(GameException::class)
	fun createTestGames() {
		createGame(UZTA)

		val game = (gameDao.read(GameID(1)) as Uzta?)!!

		val user = UztaPlayer("user")
		val user2 = UztaPlayer("user2")
		val user3 = UztaPlayer("user3")
		game.accept(JoinInput(), user)
		game.accept(JoinInput(), user2)
		game.accept(JoinInput(), user3)

		game.accept(EndStageInput(), user)

		game.accept(EndStageInput(), user)

		val currentStage = game.currentStage as FirstPlacementStage?
		val playersInOrder = currentStage!!.playersInOrder
		val firstPlayer = playersInOrder.get(0)
		val secondPlayer = playersInOrder.get(1)
		val thirdPlayer = playersInOrder.get(2)

		val edges = game.graph.edges

		val sixEdges = edges.stream()
				.limit(6)
				.collect(toList())

		selectEdge(game, firstPlayer, sixEdges[0])
		selectEdge(game, secondPlayer, sixEdges[1])
		selectEdge(game, thirdPlayer, sixEdges[2])
		selectEdge(game, thirdPlayer, sixEdges[3])
		selectEdge(game, secondPlayer, sixEdges[4])
		selectEdge(game, firstPlayer, sixEdges[5])

		log.debug("{} initialised", TestGameCreator::class.simpleName)
	}

	@Throws(GameException::class)
	private fun selectEdge(game: Uzta, firstPlayer: UztaPlayer, edge: LineSegment) {
		game.accept(SelectEdgeInput(edge.first.id, edge.second.id), firstPlayer)
	}

	private fun createGame(gameName: String) {
		gameCreatedEventHandler.handle(theGameOfGames, GameCreatedEvent(StandardPlayer(""), gameName))
	}

	companion object {
		private val log = getLogger(TestGameCreator::class)
	}
}
