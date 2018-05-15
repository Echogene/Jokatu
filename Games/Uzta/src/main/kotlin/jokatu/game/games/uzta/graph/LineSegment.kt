package jokatu.game.games.uzta.graph

import com.fasterxml.jackson.annotation.JsonValue
import jokatu.game.games.uzta.player.UztaPlayer
import ophelia.collections.iterator.StandardIterator
import ophelia.collections.pair.Pair
import ophelia.collections.pair.UnorderedPair
import java.lang.Math.max
import java.lang.Math.min
import java.util.*
import java.util.function.Consumer
import java.util.stream.Stream

class LineSegment(val first: Node, val second: Node) : Pair<Node> {
	private val nodes: UnorderedPair<Node> = UnorderedPair(first, second)

	var owner: UztaPlayer? = null

	internal val json: Map<String, Any>
		@JsonValue
		get() = object : HashMap<String, Any>() {
			init {
				put("nodes", nodes)
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
		val projection = max(0.0, min(1.0, ((point.x - first.x) * (second.x - first.x) + (point.y - first.y) * (second.y - first.y)) / squareLength
		))
		return point.squareDistanceFrom(
				first.x + projection * (second.x - first.x),
				first.y + projection * (second.y - first.y)
		)
	}

	override fun toArray(): Array<Any> {
		return nodes.toArray()
	}

	override fun <T> toArray(a: Array<T>): Array<T> {
		return nodes.toArray(a)
	}

	override fun contains(o: Any): Boolean {
		return nodes.contains(o)
	}

	override fun iterator(): StandardIterator<Node> {
		return nodes.iterator()
	}

	override fun forEach(consumer: Consumer<in Node>) {
		nodes.forEach(consumer)
	}

	override fun spliterator(): Spliterator<Node> {
		return nodes.spliterator()
	}

	override fun stream(): Stream<Node> {
		return nodes.stream()
	}

	override fun parallelStream(): Stream<Node> {
		return nodes.parallelStream()
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || javaClass != other.javaClass) return false

		val that = other as LineSegment?

		if (nodes != that!!.nodes) return false
		return if (owner != null) owner == that.owner else that.owner == null

	}

	override fun hashCode(): Int {
		var result = nodes.hashCode()
		result = 31 * result + if (owner != null) owner!!.hashCode() else 0
		return result
	}
}
