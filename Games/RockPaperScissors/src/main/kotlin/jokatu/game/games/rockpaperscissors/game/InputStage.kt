package jokatu.game.games.rockpaperscissors.game

import jokatu.game.games.rockpaperscissors.input.RockPaperScissorsInput
import jokatu.game.input.AwaitingInputEvent
import jokatu.game.input.UnacceptableInputException
import jokatu.game.player.StandardPlayer
import jokatu.game.result.PlayerResult
import jokatu.game.result.Result
import jokatu.game.result.Result.DRAW
import jokatu.game.result.Result.WIN
import jokatu.game.stage.AnyEventSingleInputStage
import jokatu.game.status.StandardTextStatus
import jokatu.game.turn.NotAPlayerException
import java.util.*
import java.util.Arrays.asList
import java.util.concurrent.ConcurrentHashMap

internal class InputStage(players: Collection<StandardPlayer>, private val status: StandardTextStatus) : AnyEventSingleInputStage<RockPaperScissorsInput, StandardPlayer>() {

	private val players: List<StandardPlayer>

	private val inputs = ConcurrentHashMap<StandardPlayer, RockPaperScissors>()

	override val inputClass: Class<RockPaperScissorsInput>
		get() = RockPaperScissorsInput::class.java

	override val playerClass: Class<StandardPlayer>
		get() = StandardPlayer::class.java

	init {
		this.players = ArrayList(players)

		status.text = "Waiting for inputs from ${this.players[0]} and ${this.players[1]}."
	}

	override fun start() {
		fireEvent(AwaitingInputEvent(players))
	}

	@Throws(Exception::class)
	override fun acceptCastInputAndPlayer(input: RockPaperScissorsInput, inputter: StandardPlayer) {

		if (!players.contains(inputter)) {
			throw NotAPlayerException()
		}

		if (inputs.containsKey(inputter)) {
			// Player has already chosen.
			throw UnacceptableInputException("You can't change your mind.")
		}
		inputs[inputter] = input.choice
		if (inputs.size == 2) {
			val player1 = players[0]
			val player2 = players[1]
			val result = inputs[player1]!!.resultAgainst(inputs[player2]!!)
			when (result) {
				WIN -> fireEvent(PlayerResult(WIN, setOf(player1)))
				Result.LOSE -> fireEvent(PlayerResult(WIN, setOf(player2)))
				else -> fireEvent(PlayerResult(DRAW, asList(player1, player2)))
			}
		} else {
			val other = getOtherPlayer(inputter)
			status.text = "Waiting for input from ${other.name}."
			fireEvent(AwaitingInputEvent(other))
		}
	}

	private fun getOtherPlayer(inputter: StandardPlayer): StandardPlayer {
		return players[(players.indexOf(inputter) + 1) % 2]
	}
}
