package jokatu.game.joining.finish

import jokatu.game.event.StageOverEvent
import jokatu.game.input.AbstractInputAcceptor
import jokatu.game.input.UnacceptableInputException
import jokatu.game.input.finishstage.EndStageInput
import jokatu.game.player.Player
import kotlin.reflect.KClass

/**
 * Accepts [EndStageInput]s and fires a [StageOverEvent] if the input is valid.
 * @param <P> the type of [Player] that should be accepted
 */
class FinishJoiningInputAcceptor<P : Player>(
		playerClass: KClass<P>,
		private val players: Map<String, P>,
		private val minimum: Int
) : AbstractInputAcceptor<EndStageInput, P, StageOverEvent>(EndStageInput::class, playerClass) {

	@Throws(Exception::class)
	override fun acceptCastInputAndPlayer(input: EndStageInput, inputter: P) {
		val more = minimum - players.size
		if (more > 0) {
			throw UnacceptableInputException(
					"There need${if (more == 1) "s" else ""} to be at least $more more "
							+ "player${if (more == 1) "" else "s"} before you can start the game."
			)
		}
		fireEvent(StageOverEvent())
	}
}
