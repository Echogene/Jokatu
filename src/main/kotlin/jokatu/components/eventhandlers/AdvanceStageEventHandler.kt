package jokatu.components.eventhandlers

import jokatu.components.stomp.GameAdvance
import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import jokatu.game.event.StageOverEvent
import org.springframework.stereotype.Component

/**
 * When a stage says it is over, move the game onto the next stage.
 */
@Component
class AdvanceStageEventHandler : SpecificEventHandler<StageOverEvent>(StageOverEvent::class) {
	override fun handleCastEvent(game: Game<*>, event: StageOverEvent) {
		game.advanceStage()

		sender.send(GameAdvance(game), true)
	}
}
