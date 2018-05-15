package jokatu.game.stage.machine

import jokatu.game.stage.Stage
import java.util.*

class SequentialStageMachine(vararg factories: () -> Stage<*>) : StageMachine {

	private var currentIndex = 0
	override var currentStage: Stage<*>? = null
	private val factories: List<() -> Stage<*>> = Arrays.asList(*factories)

	override fun advance(): Stage<*> {
		val stageFactory = factories[currentIndex++]
		currentStage = stageFactory.invoke()
		return currentStage!!
	}
}
