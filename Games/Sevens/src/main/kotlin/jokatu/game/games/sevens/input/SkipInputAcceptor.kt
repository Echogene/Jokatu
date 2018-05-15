package jokatu.game.games.sevens.input

import jokatu.game.event.StandardPublicGameEvent
import jokatu.game.games.sevens.player.SevensPlayer
import jokatu.game.input.endturn.EndTurnInputAcceptor
import jokatu.game.turn.TurnManager
import java.text.MessageFormat

class SkipInputAcceptor(turnManager: TurnManager<SevensPlayer>) : EndTurnInputAcceptor<SevensPlayer>(turnManager, SevensPlayer::class.java) {

	override fun fireAdditionalEvents(inputter: SevensPlayer) {
		fireEvent(StandardPublicGameEvent(MessageFormat.format("{0} skipped their turn.", inputter)))
	}
}
