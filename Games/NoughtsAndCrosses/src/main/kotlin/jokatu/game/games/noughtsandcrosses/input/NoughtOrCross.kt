package jokatu.game.games.noughtsandcrosses.input

import java.util.*

enum class NoughtOrCross constructor(private val displayText: String) {
	NOUGHT("⭕"), CROSS("✕");

	override fun toString(): String {
		return displayText
	}

	companion object {

		fun other(noughtOrCross: NoughtOrCross) = when (noughtOrCross) {
			NOUGHT -> CROSS
			else -> NOUGHT
		}

		fun displayValueOf(displayText: String): NoughtOrCross {
			return Arrays.stream(NoughtOrCross.values())
					.filter { noughtOrCross -> noughtOrCross.displayText == displayText }
					.findAny()
					.orElseThrow {
						IllegalArgumentException(
								"Could not find nought or cross with display text '$displayText'."
						)
					}
		}
	}
}
