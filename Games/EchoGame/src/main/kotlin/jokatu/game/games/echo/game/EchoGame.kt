package jokatu.game.games.echo.game

import jokatu.game.Game
import jokatu.game.GameID
import jokatu.game.event.StatusUpdateEvent
import jokatu.game.player.StandardPlayer
import jokatu.game.stage.machine.SingleStageMachine
import java.time.LocalTime.now
import java.time.format.DateTimeFormatter.ofPattern
import java.util.*

class EchoGame internal constructor(identifier: GameID) : Game<StandardPlayer>(identifier) {

	override val gameName: String
		get() = ECHO

	init {
		stageMachine = SingleStageMachine(EchoStage())

		Timer().scheduleAtFixedRate(
				object : TimerTask() {
					override fun run() {
						fireEvent({ { "The time is: " + now().format(ofPattern("HH:mm:ss")) } } as StatusUpdateEvent)
					}
				},
				0,
				1000
		)
	}

	companion object {
		const val ECHO = "Echo"
	}
}
