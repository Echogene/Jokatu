package jokatu.game.player

/**
 * A player is a user that participates in a game.
 * @author Steven Weston
 */
interface Player {
	val name: String
}

// This is converted to JSON using Jackson.
class PlayerStatus constructor(val isOnline: Boolean, val name: String)