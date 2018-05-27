package jokatu.game.games.sevens.input

import jokatu.game.event.StandardPublicGameEvent
import jokatu.game.games.sevens.player.SevensPlayer
import jokatu.game.input.endturn.EndTurnInputAcceptor
import jokatu.game.turn.TurnManager

class SkipInputAcceptor(
		turnManager: TurnManager<SevensPlayer>
) : EndTurnInputAcceptor<SevensPlayer>(turnManager, SevensPlayer::class) {

	override fun fireAdditionalEvents(inputter: SevensPlayer) {
		fireEvent(StandardPublicGameEvent("$inputter skipped their turn."))
	}
}
