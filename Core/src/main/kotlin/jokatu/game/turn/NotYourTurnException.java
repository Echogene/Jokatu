package jokatu.game.turn;

import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

/**
 * Thrown when a user tries to input when it's not their turn.
 */
class NotYourTurnException extends Exception {
	NotYourTurnException(@NotNull Player player) {
		super(MessageFormat.format("It''s not your turn!  Wait for {0}.", player));
	}
}
