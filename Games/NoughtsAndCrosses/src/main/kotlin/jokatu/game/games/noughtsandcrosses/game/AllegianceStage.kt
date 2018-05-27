package jokatu.game.games.noughtsandcrosses.game

import jokatu.game.event.StageOverEvent
import jokatu.game.games.noughtsandcrosses.input.AllegianceInput
import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross.Companion.other
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer
import jokatu.game.stage.SingleInputStage
import jokatu.game.status.StandardTextStatus
import ophelia.collections.set.bounded.BoundedPair

class AllegianceStage internal constructor(
		players: Collection<NoughtsAndCrossesPlayer>,
		status: StandardTextStatus
) : SingleInputStage<AllegianceInput, NoughtsAndCrossesPlayer, StageOverEvent>(AllegianceInput::class, NoughtsAndCrossesPlayer::class) {

	private val players: BoundedPair<NoughtsAndCrossesPlayer> = BoundedPair(players)

	init {
		status.text = "Waiting for either ${this.players.first} or ${this.players.second} to choose an allegiance."
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
