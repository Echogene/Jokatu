package jokatu.game.turn

import jokatu.game.player.Player

import java.text.MessageFormat

/**
 * Thrown when a user tries to input when it's not their turn.
 */
internal class NotYourTurnException(player: Player) : Exception(MessageFormat.format("It''s not your turn!  Wait for {0}.", player))
