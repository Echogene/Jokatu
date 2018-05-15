package jokatu.game.player

import jokatu.game.Game

/**
 * Creates players for users so they can interact with [Game]s.
 * @param <P> the type of [Player] to produce
 */
interface PlayerFactory<P : Player> {
	fun produce(game: Game<*>, username: String): P
}
