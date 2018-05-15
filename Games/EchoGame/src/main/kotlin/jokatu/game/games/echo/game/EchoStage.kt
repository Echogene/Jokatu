package jokatu.game.games.echo.game

import jokatu.game.event.AbstractPrivateGameEvent
import jokatu.game.games.echo.input.EchoInput
import jokatu.game.player.StandardPlayer
import jokatu.game.stage.AnyEventSingleInputStage

internal class EchoStage : AnyEventSingleInputStage<EchoInput, StandardPlayer>() {
	override val inputClass: Class<EchoInput>
		get() = EchoInput::class.java

	override val playerClass: Class<StandardPlayer>
		get() = StandardPlayer::class.java

	@Throws(Exception::class)
	override fun acceptCastInputAndPlayer(input: EchoInput, inputter: StandardPlayer) {
		fireEvent(Echo(input, inputter))
		fireEvent(object : AbstractPrivateGameEvent(setOf(inputter)) {
			override val message: String
				get() = "You said: " + input.string
		})
	}
}
