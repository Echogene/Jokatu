package jokatu.game.games.noughtsandcrosses.input;

public enum NoughtOrCross {
	NOUGHT("⭕"), CROSS("❌");

	private final String displayText;

	NoughtOrCross(String displayText) {
		this.displayText = displayText;
	}

	@Override
	public String toString() {
		return displayText;
	}

	public static NoughtOrCross other(NoughtOrCross noughtOrCross) {
		switch (noughtOrCross) {
			case NOUGHT:
				return CROSS;
			default:
				return NOUGHT;
		}
	}
}
