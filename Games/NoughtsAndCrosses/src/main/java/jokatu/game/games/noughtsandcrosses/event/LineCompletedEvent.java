package jokatu.game.games.noughtsandcrosses.event;

import jokatu.game.event.GameEvent;
import ophelia.collections.list.UnmodifiableList;
import org.jetbrains.annotations.NotNull;

public class LineCompletedEvent implements GameEvent {

	private final UnmodifiableList<Integer> line;

	public LineCompletedEvent(UnmodifiableList<Integer> line) {
		this.line = line;
	}

	@NotNull
	@Override
	public String getMessage() {
		return line.toString();
	}
}
