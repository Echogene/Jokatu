package jokatu.game

import jokatu.components.dao.GameDao
import jokatu.game.player.Player
import jokatu.identity.Identifier
import org.springframework.beans.factory.annotation.Autowired

import java.util.concurrent.atomic.AtomicLong

/**
 * An abstract version of game factory that stores a common identifier to give IDs to games in subclasses.
 */
abstract class AbstractGameFactory<G : Game<out Player>> : GameFactory<G> {

	@Autowired
	private lateinit var gameDao: GameDao

	override fun produceGame(creatorName: String): G {
		val game = produce(GAME_IDENTIFIER.get(), creatorName)
		gameDao.register(game)
		return game
	}

	protected abstract fun produce(gameID: GameID, creatorName: String): G

	companion object {
		private val GAME_IDENTIFIER = object : Identifier<GameID> {
			private val id = AtomicLong(0)

			override fun get(): GameID {
				return GameID(id.getAndIncrement())
			}
		}
	}
}
