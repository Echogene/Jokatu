package jokatu.game.games.uzta.graph

import com.fasterxml.jackson.annotation.JsonValue
import jokatu.game.games.uzta.player.UztaPlayer
import ophelia.kotlin.collections.pair.UnorderedPair
import java.lang.Math.max
import java.lang.Math.min
import java.util.*

class LineSegment(first: Node, second: Node) : UnorderedPair<Node>(first, second) {

	var owner: UztaPlayer? = null

	internal val json: Map<String, Any>
		@JsonValue
		get() = object : HashMap<String, Any>() {
			init {
				put("nodes", listOf(first, second))
				if (owner != null) {
					put("colour", owner!!.colour!!)
				}
			}
		}

	fun squareDistanceFrom(point: Node): Double {
		val squareLength = first.squareDistanceFrom(second)
		if (squareLength == 0.0) {
			return point.squareDistanceFrom(first)
		}
		val projection = max(
				0.0,
				min(
						1.0,
						((point.x - first.x) * (second.x - first.x)
								+ (point.y - first.y) * (second.y - first.y))
								/ squareLength
				)
		)
		return point.squareDistanceFrom(
				first.x + projection * (second.x - first.x),
				first.y + projection * (second.y - first.y)
		)
	}
}
