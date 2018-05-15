package jokatu.game.stage.machine

import jokatu.game.stage.Stage

interface StageMachine {

	val currentStage: Stage<*>?

	fun advance(): Stage<*>
}
