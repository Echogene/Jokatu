package jokatu.game.games.noughtsandcrosses.event;

import jokatu.game.event.GameEvent;
import org.jetbrains.annotations.NotNull;

public class CellChosenEvent implements GameEvent {
	private final int cell;

	public CellChosenEvent(int cell) {
		this.cell = cell;
	}

	@NotNull
	@Override
	public String getMessage() {
		return "X";
	}

	public int getCell() {
		return cell;
	}
}
