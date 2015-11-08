package jokatu.game.status;

import org.jetbrains.annotations.NotNull;

/**
 * An example game status.  Games with this type of status can either have not started, be in progress or have finished.
 */
public enum GameStatus implements Status {
	NOT_STARTED,
	IN_PROGRESS,
	OVER;

	@NotNull
	@Override
	public String getText() {
		return toString();
	}
}
