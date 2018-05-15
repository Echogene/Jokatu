package jokatu.game.games.uzta.graph

import ophelia.collections.set.HashSet
import org.ejml.simple.SimpleMatrix

class Trigon(private val Δ1: Node, Δ2: Node, Δ3: Node) {
	private val Δ2: Node
	private val Δ3: Node
	val edges: Set<LineSegment>
	val nodes: Set<Node>

	init {

		val anticlockwise = (Δ2.x - Δ1.x) * (Δ3.y - Δ1.y) - (Δ2.y - Δ1.y) * (Δ3.x - Δ1.x)
		if (anticlockwise > 0) {
			this.Δ2 = Δ2
			this.Δ3 = Δ3
		} else {
			this.Δ2 = Δ3
			this.Δ3 = Δ2
		}

		nodes = object : HashSet<Node>() {
			init {
				add(Δ1)
				add(Δ2)
				add(Δ3)
			}
		}

		edges = object : HashSet<LineSegment>() {
			init {
				add(LineSegment(Δ1, Δ2))
				add(LineSegment(Δ2, Δ3))
				add(LineSegment(Δ3, Δ1))
			}
		}
	}

	constructor(edge: LineSegment, Δ3: Node) : this(edge.first, edge.second, Δ3) {}

	fun circumcircleContains(node: Node): Boolean {
		val circumcircle = SimpleMatrix(4, 4, true,
				Δ1.x, Δ1.y, Δ1.x * Δ1.x + Δ1.y * Δ1.y, 1.0,
				Δ2.x, Δ2.y, Δ2.x * Δ2.x + Δ2.y * Δ2.y, 1.0,
				Δ3.x, Δ3.y, Δ3.x * Δ3.x + Δ3.y * Δ3.y, 1.0,
				node.x, node.y, node.x * node.x + node.y * node.y, 1.0
		)
		return circumcircle.determinant() > 0
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || javaClass != other.javaClass) return false

		val trigon = other as Trigon?

		if (Δ1 != trigon!!.Δ1) return false
		return if (Δ2 != trigon.Δ2) false else Δ3 == trigon.Δ3

	}

	override fun hashCode(): Int {
		var result = Δ1.hashCode()
		result = 31 * result + Δ2.hashCode()
		result = 31 * result + Δ3.hashCode()
		return result
	}
}
