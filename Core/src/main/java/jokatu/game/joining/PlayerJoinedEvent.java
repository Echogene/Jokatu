package jokatu.game.joining;

import jokatu.game.event.PublicGameEvent;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public class PlayerJoinedEvent implements PublicGameEvent {

	private final String message;

	public PlayerJoinedEvent(Player player) {
		message = player.getName() + " joined the game.";
	}

	@NotNull
	@Override
	public String getMessage() {
		return message;
	}
}
