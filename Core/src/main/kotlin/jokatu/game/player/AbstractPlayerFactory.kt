package jokatu.game.player

import jokatu.game.Game

import java.text.MessageFormat

/**
 * A [PlayerFactory] that produces players for a specific game type.
 * @param <P> the type of [Player] to produce
 * @param <G> the type of [Game] to create the players for
 */
abstract class AbstractPlayerFactory<P : Player, G : Game<P>> : PlayerFactory<P> {

	protected abstract val gameClass: Class<G>

	private fun castGame(game: Game<*>): G {
		val gameClass = gameClass
		if (!gameClass.isInstance(game)) {
			throw IllegalArgumentException(
					MessageFormat.format("Game was not of the right type.  Expected {0}.", gameClass)
			)
		}
		return gameClass.cast(game)
	}

	override fun produce(game: Game<*>, username: String): P {
		return produceInCastGame(castGame(game), username)
	}

	protected abstract fun produceInCastGame(g: G, username: String): P
}
