package jokatu.game.joining

/**
 * Thrown when a player tries to join the same game twice.
 */
class PlayerAlreadyJoinedException internal constructor() : CannotJoinGameException("You cannot join the game twice!")
