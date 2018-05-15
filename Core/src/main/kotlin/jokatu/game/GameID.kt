package jokatu.game

import com.fasterxml.jackson.annotation.JsonValue
import jokatu.identity.Identity
import java.lang.Long.toString

/**
 * An object that uniquely identifies a game.
 * @author Steven Weston
 */
class GameID : Identity, Comparable<GameID> {

	// This is called by Jackson
	@get:JsonValue
	var identity: Long = 0

	// This is called by Jackson
	constructor()

	constructor(identity: Long) {
		this.identity = identity
	}

	override fun equals(other: Any?): Boolean {

		if (this === other) {
			return true
		}
		if (other == null || javaClass != other.javaClass) {
			return false
		}

		val gameID = other as GameID?

		return identity == gameID!!.identity
	}

	override fun hashCode(): Int {
		return (identity xor identity.ushr(32)).toInt()
	}

	override fun toString(): String {
		return toString(identity)
	}

	override fun compareTo(other: GameID): Int {
		return when {
			identity < other.identity -> -1
			identity > other.identity -> 1
			else -> 0
		}
	}
}
