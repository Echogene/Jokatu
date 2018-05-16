package jokatu.game.input

/**
 * An exception thrown when a user's input is not acceptable.  This could mean that the user sent badly-formed input or
 * they sent it at the wrong time.  If one of these is thrown, it is probably an indication that either the frontend
 * has a bug that sends an input or something is tampering with the user's requests.
 */
class UnacceptableInputException : Exception {

	constructor(message: String) : super(message)

	constructor(message: String, cause: Throwable) : super(message, cause)
}
