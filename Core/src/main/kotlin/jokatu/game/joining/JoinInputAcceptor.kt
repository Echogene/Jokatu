package jokatu.game.joining

import jokatu.game.input.AbstractInputAcceptor
import jokatu.game.player.Player

/**
 * Accepts [JoinInput]s from players and adds them to the game if there is enough room.  It fires a
 * [PlayerJoinedEvent] if the joining was successful.
 * @param <P> the type of [Player] to accept input from
 */
class JoinInputAcceptor<P : Player>(
		override val playerClass: Class<P>, private val players: MutableMap<String, P>,
		/**
		 * The maximum number of players that can join the game needs before it starts.
		 */
		private val maximum: Int
) : AbstractInputAcceptor<JoinInput, P, PlayerJoinedEvent>() {

	override val inputClass: Class<JoinInput>
		get() = JoinInput::class.java

	@Throws(Exception::class)
	override fun acceptCastInputAndPlayer(input: JoinInput, inputter: P) {
		checkCanJoin(inputter)
		players[inputter.name] = inputter
		fireEvent(PlayerJoinedEvent(inputter))
	}

	@Throws(CannotJoinGameException::class)
	private fun checkCanJoin(inputter: P) {
		if (players.size > maximum - 1) {
			throw GameFullException()
		}
		if (players.containsKey(inputter.name)) {
			throw PlayerAlreadyJoinedException()
		}
	}
}
