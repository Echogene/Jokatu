package jokatu.game.games.noughtsandcrosses.event;

import jokatu.game.event.GameEvent;
import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross;
import org.jetbrains.annotations.NotNull;

public class CellChosenEvent implements GameEvent {
	private final int cell;
	private final NoughtOrCross noughtOrCross;

	public CellChosenEvent(int cell, NoughtOrCross noughtOrCross) {
		this.cell = cell;
		this.noughtOrCross = noughtOrCross;
	}

	@NotNull
	@Override
	public String getMessage() {
		return noughtOrCross.toString();
	}

	public int getCell() {
		return cell;
	}
}
