package jokatu.game.games.sevens.event

import jokatu.game.cards.Card
import jokatu.game.event.PrivateGameEvent
import jokatu.game.games.sevens.player.SevensPlayer

class CardDrawnEvent(override val player: SevensPlayer, private val drawn: Card) : PrivateGameEvent, HandChangedEvent {

	override val message: String
		get() = "You drew ${drawn.label}."

	override val players: Collection<SevensPlayer>
		get() = setOf(player)
}
