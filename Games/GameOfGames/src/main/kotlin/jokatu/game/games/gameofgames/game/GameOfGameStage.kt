package jokatu.game.games.gameofgames.game

import jokatu.game.games.gameofgames.event.GameCreatedEvent
import jokatu.game.games.gameofgames.input.CreateGameInput
import jokatu.game.player.StandardPlayer
import jokatu.game.stage.SingleInputStage

internal class GameOfGameStage : SingleInputStage<CreateGameInput, StandardPlayer, GameCreatedEvent>() {
	override val inputClass: Class<CreateGameInput>
		get() = CreateGameInput::class.java

	override val playerClass: Class<StandardPlayer>
		get() = StandardPlayer::class.java

	@Throws(Exception::class)
	override fun acceptCastInputAndPlayer(input: CreateGameInput, inputter: StandardPlayer) {
		val gameName = input.gameName
		fireEvent(GameCreatedEvent(inputter, gameName))
	}
}
