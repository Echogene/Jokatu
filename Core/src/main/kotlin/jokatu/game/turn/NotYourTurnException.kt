package jokatu.game.turn

import jokatu.game.player.Player

/**
 * Thrown when a user tries to input when it's not their turn.
 */
internal class NotYourTurnException(player: Player, actualPlayer: Player)
	: Exception("It's not your turn, $player!  Wait for $actualPlayer.")
