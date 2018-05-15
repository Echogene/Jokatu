package jokatu.game.joining.finish

import jokatu.game.event.StageOverEvent
import jokatu.game.input.AbstractInputAcceptor
import jokatu.game.input.UnacceptableInputException
import jokatu.game.input.finishstage.EndStageInput
import jokatu.game.player.Player

/**
 * Accepts [EndStageInput]s and fires a [StageOverEvent] if the input is valid.
 * @param <P> the type of [Player] that should be accepted
</P> */
class FinishJoiningInputAcceptor<P : Player>(override val playerClass: Class<P>, private val players: Map<String, P>, private val minimum: Int) : AbstractInputAcceptor<EndStageInput, P, StageOverEvent>() {

	override val inputClass: Class<EndStageInput>
		get() = EndStageInput::class.java

	@Throws(Exception::class)
	override fun acceptCastInputAndPlayer(input: EndStageInput, inputter: P) {
		val more = minimum - players.size
		if (more > 0) {
			throw UnacceptableInputException(
					"There need{0} to be at least {1} more player{2} before you can start the game.",
					if (more == 1) "s" else "",
					more,
					if (more == 1) "" else "s"
			)
		}
		fireEvent(StageOverEvent())
	}
}
