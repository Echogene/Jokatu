package jokatu.game.turn;

import jokatu.game.event.PublicGameEvent;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class TurnChangedEvent implements PublicGameEvent {

	private final Player oldPlayer;
	private final Player newPlayer;

	TurnChangedEvent(@NotNull Player oldPlayer, @NotNull Player newPlayer) {
		this.oldPlayer = oldPlayer;
		this.newPlayer = newPlayer;
	}

	@NotNull
	@Override
	public String getMessage() {
		return MessageFormat.format("{0}'s turn is over.  It's now {1}'s turn.", oldPlayer, newPlayer);
	}

	@NotNull
	public Player getNewPlayer() {
		return newPlayer;
	}
}
