package jokatu.game.exception

import jokatu.game.GameID

/**
 * An exception that occurs within a particular game.
 * @author steven
 */
class GameException : Exception {

	// I think this can be converted to JSON using Jackson.
	val id: GameID

	constructor(id: GameID, message: String) : super(message) {
		this.id = id
	}

	constructor(
			id: GameID,
			cause: Throwable,
			message: String
	) : super(message, cause) {
		this.id = id
	}
}
