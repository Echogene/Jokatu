package jokatu.game.viewresolver

import jokatu.game.Game
import jokatu.game.player.Player
import org.springframework.web.servlet.ModelAndView
import kotlin.reflect.KClass
import kotlin.reflect.full.cast

/**
 * Determine which view a player should see when requesting a game.
 * @author steven
 */
abstract class ViewResolver<P : Player, G : Game<P>> protected constructor(
		protected val playerClass: KClass<P>,
		protected val game: G
) {

	val viewForObserver: ModelAndView
		get() = defaultView

	protected abstract val defaultView: ModelAndView

	fun getViewForPlayer(player: Player): ModelAndView {
		val castPlayer = castPlayer(player)
		return getViewFor(castPlayer)
	}

	private fun castPlayer(player: Player): P {
		val playerClass = playerClass
		if (!playerClass.isInstance(player)) {
			throw IllegalArgumentException(
					"Player was not of the right type.  Expected ${playerClass.simpleName} but was ${player.javaClass.simpleName}."
			)
		}
		return playerClass.cast(player)
	}

	protected abstract fun getViewFor(player: P): ModelAndView
}
