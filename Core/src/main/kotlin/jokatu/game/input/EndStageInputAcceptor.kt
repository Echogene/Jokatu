package jokatu.game.input

import jokatu.game.event.StageOverEvent
import jokatu.game.input.finishstage.EndStageInput
import jokatu.game.player.Player
import ophelia.collections.BaseCollection

class EndStageInputAcceptor<P : Player>(override val playerClass: Class<P>, private val players: BaseCollection<P>) : AbstractInputAcceptor<EndStageInput, P, StageOverEvent>() {

	override val inputClass: Class<EndStageInput>
		get() = EndStageInput::class.java

	@Throws(Exception::class)
	override fun acceptCastInputAndPlayer(input: EndStageInput, inputter: P) {
		if (!players.contains(inputter)) {
			throw UnacceptableInputException("You can't end the stage if you're not playing!")
		}
		fireEvent(StageOverEvent())
	}
}
