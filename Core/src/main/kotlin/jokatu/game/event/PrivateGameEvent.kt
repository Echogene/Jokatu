package jokatu.game.event

import jokatu.game.player.Player

/**
 * An event that occurs privately for a collection of players.
 * @author steven
 */
interface PrivateGameEvent : MessagedGameEvent {

	/**
	 * @return the players for whom this has a private message
	 */
	val players: Collection<Player>
}
