package jokatu.game.games.uzta.graph

enum class NodeType private constructor(
		private val singular: String,
		val plural: String,
		val symbol: String
) {
	SQUARE("square", "squares", "□"),
	CIRCLE("circle", "circles", "○"),
	RHOMBUS("rhombus", "rhombi", "◇");

	fun getNumber(number: Int): String {
		return "$number ${if (number == 1) singular else plural}"
	}
}
