package jokatu.game.games.uzta.input;

import jokatu.game.input.Input;
import org.jetbrains.annotations.NotNull;

public class SelectEdgeInput implements Input {

	private final String startId;
	private final String endId;

	SelectEdgeInput(@NotNull String startId, @NotNull String endId) {
		this.startId = startId;
		this.endId = endId;
	}

	@NotNull
	public String getStartId() {
		return startId;
	}

	@NotNull
	public String getEndId() {
		return endId;
	}
}
