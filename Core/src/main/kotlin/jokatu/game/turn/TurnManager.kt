package jokatu.game.turn

import jokatu.game.player.Player
import ophelia.event.observable.AbstractSynchronousObservable

/**
 * Manages whose turn it is.  Fires a [TurnChangedEvent] event when the turn changes.
 */
class TurnManager<P : Player>(private val players: List<P>) : AbstractSynchronousObservable<TurnChangedEvent>() {

	var currentPlayer: P? = null
		set(newPlayer) {
			val oldPlayer = this.currentPlayer
			field = newPlayer
			fireEvent(TurnChangedEvent(oldPlayer, this.currentPlayer!!))
		}

	/**
	 * Assert that the given player is both an actual player and the current player.
	 * @param player this should be the current player
	 * @throws NotYourTurnException if the given player is not the current player
	 */
	@Throws(NotYourTurnException::class, NotAPlayerException::class)
	fun assertCurrentPlayer(player: P) {
		if (!players.contains(player)) {
			throw NotAPlayerException()
		}
		if (player !== this.currentPlayer) {
			throw NotYourTurnException(this.currentPlayer!!)
		}
	}

	/**
	 * Pass the turn onto the next player.
	 */
	operator fun next() {
		val i = players.indexOf(this.currentPlayer)
		currentPlayer = players[(i + 1) % players.size]
	}

	/**
	 * Pass the turn onto the previous player.
	 */
	fun previous() {
		val i = players.indexOf(this.currentPlayer)
		currentPlayer = players[(i + players.size - 1) % players.size]
	}

	fun playAgain() {
		currentPlayer = this.currentPlayer
	}
}
