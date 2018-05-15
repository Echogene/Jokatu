package jokatu.components.eventhandlers

import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import jokatu.game.event.StageOverEvent
import org.springframework.stereotype.Component

/**
 * When a stage says it is over, move the game onto the next stage.
 */
@Component
class AdvanceStageEventHandler : SpecificEventHandler<StageOverEvent>() {

	override val eventClass: Class<StageOverEvent>
		get() = StageOverEvent::class.java

	override fun handleCastEvent(game: Game<*>, event: StageOverEvent) {
		game.advanceStage()

		sender.send("/topic/advance.game." + game.identifier, true)
	}
}
