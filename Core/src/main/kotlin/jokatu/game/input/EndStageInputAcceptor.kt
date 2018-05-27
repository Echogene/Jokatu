package jokatu.game.input

import jokatu.game.event.StageOverEvent
import jokatu.game.input.finishstage.EndStageInput
import jokatu.game.player.Player
import ophelia.collections.BaseCollection
import kotlin.reflect.KClass

class EndStageInputAcceptor<P : Player>(
		playerClass: KClass<P>,
		private val players: BaseCollection<P>
) : AbstractInputAcceptor<EndStageInput, P, StageOverEvent>(EndStageInput::class, playerClass) {

	@Throws(Exception::class)
	override fun acceptCastInputAndPlayer(input: EndStageInput, inputter: P) {
		if (!players.contains(inputter)) {
			throw UnacceptableInputException("You can't end the stage if you're not playing!")
		}
		fireEvent(StageOverEvent())
	}
}
