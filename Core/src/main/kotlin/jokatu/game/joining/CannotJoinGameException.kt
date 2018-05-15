package jokatu.game.joining

/**
 * Thrown to say that a player cannot join a game for some reason.
 */
open class CannotJoinGameException(message: String) : Exception(message)
