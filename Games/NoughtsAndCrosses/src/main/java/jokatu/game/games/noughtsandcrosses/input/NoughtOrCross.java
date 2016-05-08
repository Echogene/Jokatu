package jokatu.game.games.noughtsandcrosses.input;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Arrays;

public enum NoughtOrCross {
	NOUGHT("⭕"), CROSS("✕");

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

	@NotNull
	public static NoughtOrCross displayValueOf(String displayText) {
		return Arrays.stream(NoughtOrCross.values())
				.filter(noughtOrCross -> noughtOrCross.displayText.equals(displayText))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException(MessageFormat.format(
						"Could not find nought or cross with display text ''{0}''",
						displayText
				)));
	}
}
