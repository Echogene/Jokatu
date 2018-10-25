package jokatu.game.games.uzta.graph

import org.ejml.simple.SimpleMatrix

/**
 * A trigon (AKA triangle) has three points.
 */
class Trigon(
		/**
		 * The first point of the trigon.
		 */
		private val Δ1: Node,
		Δ2: Node,
		Δ3: Node
) {
	/**
	 * The second point of the trigon.  It is the next one anticlockwise (about the centre) from [Δ1].
	 */
	private val Δ2: Node

	/**
	 * The third point of the trigon.  It is the next one anticlockwise (about the centre) from [Δ2], and therefore the
	 * next one clockwise from [Δ1].
	 */
	private val Δ3: Node

	/**
	 * The set of three edges of this trigon.
	 */
	val edges: Set<LineSegment>

	/**
	 * The set of three points of this trigon.
	 */
	val nodes: Set<Node>

	constructor(edge: LineSegment, Δ3: Node) : this(edge.first, edge.second, Δ3)

	init {
		val anticlockwise = (Δ2.x - Δ1.x) * (Δ3.y - Δ1.y) - (Δ2.y - Δ1.y) * (Δ3.x - Δ1.x)
		if (anticlockwise > 0) {
			this.Δ2 = Δ2
			this.Δ3 = Δ3
		} else {
			this.Δ2 = Δ3
			this.Δ3 = Δ2
		}

		nodes = setOf(Δ1, Δ2, Δ3)

		edges = setOf(LineSegment(Δ1, Δ2), LineSegment(Δ2, Δ3), LineSegment(Δ3, Δ1))
	}

	/**
	 * Does the circumcircle of this trigon contain the given node?
	 */
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
		if (javaClass != other?.javaClass) return false

		other as Trigon

		if (Δ1 != other.Δ1) return false
		if (Δ2 != other.Δ2) return false
		if (Δ3 != other.Δ3) return false

		return true
	}

	override fun hashCode(): Int {
		var result = Δ1.hashCode()
		result = 31 * result + Δ2.hashCode()
		result = 31 * result + Δ3.hashCode()
		return result
	}
}
