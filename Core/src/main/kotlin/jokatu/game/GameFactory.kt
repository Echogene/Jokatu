package jokatu.game

import jokatu.game.player.Player

/**
 * This creates games of a specific type.
 */
interface GameFactory<G : Game<out Player>> {

	/**
	 * @param creatorName the name of the user who is creating the game.  They can get special options while setting up
	 * the game.  They don't necessarily need to join the game.
	 * @return a new game
	 */
	fun produceGame(creatorName: String): G
}
