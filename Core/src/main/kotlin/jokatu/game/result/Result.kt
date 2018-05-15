package jokatu.game.result

/**
 * A standard result for any game.
 */
enum class Result constructor(
		private val singular3rdPersonPresent: String,
		private val plural3rdPersonPresent: String
) {
	WIN("wins", "win"),
	DRAW("draws", "draw"),
	LOSE("loses", "lose");

	fun get3rdPersonPresent(number: Int): String {
		return if (number == 1) singular3rdPersonPresent else plural3rdPersonPresent
	}
}
