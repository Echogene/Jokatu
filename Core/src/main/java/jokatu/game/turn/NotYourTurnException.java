package jokatu.game.turn;

import jokatu.game.player.Player;

import java.text.MessageFormat;

public class NotYourTurnException extends Exception {
	NotYourTurnException(Player player) {
		super(MessageFormat.format("It''s not your turn!  Wait for {0}.", player));
	}
}
