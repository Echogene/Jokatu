package jokatu.game.stage.machine

import jokatu.game.stage.Stage

class SingleStageMachine(override val currentStage: Stage<*>) : StageMachine {
	override fun advance(): Stage<*> {
		return currentStage
	}
}
