package jokatu.game.status;

import org.jetbrains.annotations.NotNull;

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
