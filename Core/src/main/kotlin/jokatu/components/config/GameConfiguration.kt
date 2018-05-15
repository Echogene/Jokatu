package jokatu.components.config

import jokatu.game.GameFactory
import jokatu.game.player.AbstractPlayerFactory
import jokatu.game.player.PlayerFactory
import jokatu.game.viewresolver.ViewResolverFactory

/**
 * Groups together all the necessary components for a type of game.
 * @author steven
 */
interface GameConfiguration {

	/**
	 * @return the [GameFactory] that will be used to construct games of this type
	 */
	val gameFactory: GameFactory<*>

	/**
	 * @return the [AbstractPlayerFactory] that will be used to create players for games of this type
	 */
	val playerFactory: PlayerFactory<*>

	/**
	 * @return the [ViewResolverFactory] that will be used to determine the views that the client should use for
	 * games of this type
	 */
	val viewResolverFactory: ViewResolverFactory<*, *>
}
