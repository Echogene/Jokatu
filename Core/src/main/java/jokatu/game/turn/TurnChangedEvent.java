package jokatu.game.turn;

import jokatu.game.event.PublicGameEvent;
import jokatu.game.input.AwaitingInputEvent;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.text.MessageFormat.format;

/**
 * Fired when play is passed from one player to another.
 */
public class TurnChangedEvent extends AwaitingInputEvent implements PublicGameEvent {

	private final Player oldPlayer;
	private final Player newPlayer;

	TurnChangedEvent(@Nullable Player oldPlayer, @NotNull Player newPlayer) {
		super(newPlayer);
		this.oldPlayer = oldPlayer;
		this.newPlayer = newPlayer;
	}

	@NotNull
	@Override
	public String getMessage() {
		if (oldPlayer == null) {
			return format("It''s {0}''s turn.", newPlayer);
		} else if (oldPlayer == newPlayer) {
			return format("It's {0}''s turn again.", oldPlayer);
		} else {
			return format("{0}''s turn is over.  It''s now {1}''s turn.", oldPlayer, newPlayer);
		}
	}

	@NotNull
	public Player getNewPlayer() {
		return newPlayer;
	}
}
