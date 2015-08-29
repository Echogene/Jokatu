package jokatu.game.result;

import jokatu.game.event.PublicGameEvent;
import jokatu.game.user.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * An endgame result for multiple players.  When a team wins, or multiple players draw, the set of players will have
 * more than one element.
 */
public class PlayerResult implements PublicGameEvent {

	private final String message;

	public PlayerResult(Result result, Collection<? extends Player> players) {
		message = players.toString() + " " + result.toString();
	}

	@NotNull
	@Override
	public String getMessage() {
		return message;
	}
}
