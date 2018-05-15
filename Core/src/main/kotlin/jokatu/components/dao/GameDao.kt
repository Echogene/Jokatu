package jokatu.components.dao

import jokatu.game.Game
import jokatu.game.GameID
import jokatu.game.player.Player
import jokatu.identity.IdentifiableDao
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

/**
 * A dao that grants access to all games currently registered by referring to the game's ID.
 * @author Steven Weston
 */
@Component
class GameDao : IdentifiableDao<GameID, Game<out Player>> {

	private val games = ConcurrentHashMap<GameID, Game<out Player>>()

	override fun read(identity: GameID): Game<out Player>? {
		return games[identity]
	}

	override fun count(): Long {
		return games.size.toLong()
	}

	override fun register(identifiable: Game<out Player>) {
		games[identifiable.identifier] = identifiable
	}
}
