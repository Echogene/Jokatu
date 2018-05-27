package jokatu.game.games.noughtsandcrosses.game

import jokatu.game.games.noughtsandcrosses.event.CellChosenEvent
import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross
import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross.CROSS
import jokatu.game.games.noughtsandcrosses.input.NoughtsAndCrossesInput
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer
import jokatu.game.input.UnacceptableInputException
import jokatu.game.result.PlayerResult
import jokatu.game.result.Result.DRAW
import jokatu.game.result.Result.WIN
import jokatu.game.stage.AnyEventSingleInputStage
import jokatu.game.status.StandardTextStatus
import jokatu.game.turn.TurnManager
import ophelia.collections.list.UnmodifiableList
import ophelia.collections.set.HashSet
import ophelia.collections.set.UnmodifiableSet
import java.util.*
import java.util.Arrays.asList
import java.util.Arrays.stream
import java.util.stream.Collectors
import java.util.stream.IntStream

internal class InputStage(
		players: Collection<NoughtsAndCrossesPlayer>,
		status: StandardTextStatus
) : AnyEventSingleInputStage<NoughtsAndCrossesInput, NoughtsAndCrossesPlayer>(NoughtsAndCrossesInput::class, NoughtsAndCrossesPlayer::class) {

	private val inputs: MutableMap<Int, NoughtOrCross> = HashMap()

	private val players: ArrayList<NoughtsAndCrossesPlayer> = ArrayList(players)
	private val turnManager: TurnManager<NoughtsAndCrossesPlayer>

	private val completedLines: List<Line>
		get() = stream(NoughtOrCross.values()).map({ this.fireLineCompletedEventsForPlayer(it) })
				.flatMap({ it.stream() })
				.map({ Line(it) })
				.collect(Collectors.toList())

	init {
		turnManager = TurnManager(this.players)
		turnManager.observe { e ->
			status.text = "Waiting for ${e.newPlayer} to choose a cell."
			// Forward the event.
			fireEvent(e)
		}
	}

	override fun start() {
		// Crosses go first.
		val startingPlayer = players.stream()
				.filter { player -> player.allegiance == CROSS }
				.findAny()
				.orElseThrow { RuntimeException("No player was aligned to crosses.") }
		turnManager.currentPlayer = startingPlayer
	}

	@Throws(Exception::class)
	override fun acceptCastInputAndPlayer(input: NoughtsAndCrossesInput, inputter: NoughtsAndCrossesPlayer) {
		turnManager.assertCurrentPlayer(inputter)

		val cell = input.cellId
		if (inputs.containsKey(cell)) {
			throw UnacceptableInputException("Cell $cell already contained a ${inputs[cell]}.")
		}
		inputs[cell] = inputter.allegiance!!

		val lines = completedLines
		fireEvent(CellChosenEvent(cell, inputs[cell]!!, lines))
		if (!lines.isEmpty()) {
			fireEvent(PlayerResult(WIN, setOf(inputter)))
		} else if (inputs.size == 9) {
			fireEvent(PlayerResult(DRAW, players))
		} else {
			turnManager.next()
		}
	}

	private fun fireLineCompletedEventsForPlayer(noughtOrCross: NoughtOrCross): List<UnmodifiableList<Int>> {
		val cellsForPlayer = getCellsForPlayer(noughtOrCross)
		return LINES.stream()
				.filter { line -> line.stream().allMatch({ cellsForPlayer.contains(it) }) }
				.collect(Collectors.toList())
	}

	private fun getCellsForPlayer(noughtOrCross: NoughtOrCross): Set<Int> {
		return inputs.entries.stream()
				.filter { entry -> entry.value == noughtOrCross }
				.map({ it.key })
				.collect(Collectors.toSet())
	}

	companion object {
		private val LINES: UnmodifiableSet<UnmodifiableList<Int>>

		init {
			val lines = HashSet<UnmodifiableList<Int>>()
			IntStream.range(0, 3)
					.map { i -> i * 3 }
					.forEach { i -> lines.add(UnmodifiableList(asList(i, i + 1, i + 2))) }
			IntStream.range(0, 3)
					.forEach { i -> lines.add(UnmodifiableList(asList(i, i + 3, i + 6))) }
			lines.add(UnmodifiableList(asList(0, 4, 8)))
			lines.add(UnmodifiableList(asList(2, 4, 6)))
			LINES = UnmodifiableSet(lines)
		}
	}
}
