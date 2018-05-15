package jokatu.game.player

/**
 * An abstract player has a name.
 */
open class AbstractPlayer protected constructor(override val name: String) : Player {

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || javaClass != other.javaClass) return false

		val that = other as AbstractPlayer?

		return name == that!!.name
	}

	override fun hashCode(): Int {
		return name.hashCode()
	}

	override fun toString(): String {
		return name
	}
}
