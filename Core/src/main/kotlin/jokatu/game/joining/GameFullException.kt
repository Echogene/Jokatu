package jokatu.game.joining

import jokatu.game.Game

/**
 * Thrown to indicate that a [Game] already has the maximum number of players.
 */
class GameFullException internal constructor() : CannotJoinGameException("No more players can join.")
