package jokatu.game.exception

import jokatu.game.GameID

import java.text.MessageFormat

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

	constructor(id: GameID, cause: Throwable, pattern: String, vararg arguments: Any?) : this(id, cause, MessageFormat.format(pattern, *arguments))

	constructor(id: GameID, pattern: String, vararg arguments: Any?) : this(id, MessageFormat.format(pattern, *arguments))
}
