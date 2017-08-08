package jokatu.game.games.noughtsandcrosses.game;

import ophelia.collections.list.UnmodifiableList;

@SuppressWarnings("unused") // This is converted to JSON using Jackson.
public class Line {
	private final String start;
	private final String end;

	Line(UnmodifiableList<Integer> cells) {
		this.start = "cell_" + cells.get(0);
		this.end = "cell_" + cells.get(cells.size() - 1);
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}
}
