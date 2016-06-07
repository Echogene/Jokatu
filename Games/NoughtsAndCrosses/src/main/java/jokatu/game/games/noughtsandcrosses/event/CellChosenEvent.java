package jokatu.game.games.noughtsandcrosses.event;

import jokatu.game.event.GameEvent;
import jokatu.game.games.noughtsandcrosses.game.Line;
import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CellChosenEvent implements GameEvent {
	private final int cell;
	private final NoughtOrCross noughtOrCross;
	private final List<Line> lines;

	public CellChosenEvent(int cell, NoughtOrCross noughtOrCross, List<Line> lines) {
		this.cell = cell;
		this.noughtOrCross = noughtOrCross;
		this.lines = lines;
	}

	@NotNull
	public String getNoughtOrCross() {
		return noughtOrCross.toString();
	}

	public int getCell() {
		return cell;
	}

	public List<Line> getLines() {
		return lines;
	}
}
