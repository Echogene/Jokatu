package jokatu.game.games.uzta.graph

import ophelia.collections.set.HashSet
import java.util.*
import java.util.Comparator.comparingDouble

class Node(val id: String, val x: Double, val y: Double) {

	var type: NodeType? = null
	val values = HashSet<Int>()
	var isHighlighted = false

	fun addValue(value: Int) {
		values.add(value)
	}

	fun distanceFrom(other: Node): Double {
		return Math.sqrt(squareDistanceFrom(other))
	}

	fun squareDistanceFrom(other: Node): Double {
		return squareDistanceFrom(other.x, other.y)
	}

	fun squareDistanceFrom(x: Double, y: Double): Double {
		val δx = this.x - x
		val δy = this.y - y
		return δx * δx + δy * δy
	}

	fun nearest(): Comparator<Node> {
		return comparingDouble { this.squareDistanceFrom(it) }
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || javaClass != other.javaClass) return false

		val node = other as Node?

		return id == node!!.id

	}

	override fun hashCode(): Int {
		return id.hashCode()
	}
}
