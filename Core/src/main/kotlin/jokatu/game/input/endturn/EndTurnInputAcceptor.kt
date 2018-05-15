package jokatu.game.input.endturn

import jokatu.game.input.AnyEventInputAcceptor
import jokatu.game.player.Player
import jokatu.game.turn.TurnChangedEvent
import jokatu.game.turn.TurnManager

open class EndTurnInputAcceptor<P : Player>(private val turnManager: TurnManager<P>, override val playerClass: Class<P>) : AnyEventInputAcceptor<EndTurnInput, P>() {

	override val inputClass: Class<EndTurnInput>
		get() = EndTurnInput::class.java

	@Throws(Exception::class)
	override fun acceptCastInputAndPlayer(input: EndTurnInput, inputter: P) {
		turnManager.assertCurrentPlayer(inputter)

		fireAdditionalEvents(inputter)

		turnManager.next()
	}

	/**
	 * Override this method to fire additional events.  It happens after we know that the current turn is actually the
	 * inputter's, but before the [TurnChangedEvent] for the next turn is fired.
	 *
	 * @param inputter the player that ended their turn
	 */
	protected open fun fireAdditionalEvents(inputter: P) {
		// Override me!
	}
}
