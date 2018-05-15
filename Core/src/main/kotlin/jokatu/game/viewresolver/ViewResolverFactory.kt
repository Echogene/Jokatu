package jokatu.game.viewresolver

import jokatu.game.Game
import jokatu.game.player.Player

import java.text.MessageFormat

/**
 * A factory for [ViewResolver]s.
 * @author steven
 */
abstract class ViewResolverFactory<P : Player, G : Game<P>> {

	protected abstract val gameClass: Class<G>

	fun getViewResolver(game: Game<*>): ViewResolver<P, G> {
		val castGame = castGame(game)
		return getResolverFor(castGame)
	}

	private fun castGame(game: Game<*>): G {
		val gameClass = gameClass
		if (!gameClass.isInstance(game)) {
			throw IllegalArgumentException(
					MessageFormat.format("Game was not of the right type.  Expected {0}.", gameClass)
			)
		}
		return gameClass.cast(game)
	}

	protected abstract fun getResolverFor(castGame: G): ViewResolver<P, G>
}
