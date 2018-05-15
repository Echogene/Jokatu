package jokatu.game.games.noughtsandcrosses.game

import jokatu.game.event.StageOverEvent
import jokatu.game.games.noughtsandcrosses.input.AllegianceInput
import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross.Companion.other
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer
import jokatu.game.stage.SingleInputStage
import jokatu.game.status.StandardTextStatus
import ophelia.collections.set.bounded.BoundedPair

class AllegianceStage internal constructor(players: Collection<NoughtsAndCrossesPlayer>, status: StandardTextStatus) : SingleInputStage<AllegianceInput, NoughtsAndCrossesPlayer, StageOverEvent>() {

	private val players: BoundedPair<NoughtsAndCrossesPlayer> = BoundedPair(players)

	override val inputClass: Class<AllegianceInput>
		get() = AllegianceInput::class.java

	override val playerClass: Class<NoughtsAndCrossesPlayer>
		get() = NoughtsAndCrossesPlayer::class.java

	init {
		status.setText(
				"Waiting for either {0} or {1} to choose an allegiance.",
				this.players.first!!,
				this.players.second!!
		)
	}

	@Throws(Exception::class)
	override fun acceptCastInputAndPlayer(input: AllegianceInput, inputter: NoughtsAndCrossesPlayer) {
		if (inputter.allegiance == null) {
			val allegiance = input.noughtOrCross
			val other = players.getOther(inputter) ?: throw Exception("The other player could not be found.")
			inputter.allegiance = allegiance
			other.allegiance = other(allegiance)
			fireEvent(StageOverEvent())
		} else {
			throw AllegianceAlreadyChosenException()
		}
	}

	private class AllegianceAlreadyChosenException internal constructor() : Exception("You can't change your allegiance.")
}
