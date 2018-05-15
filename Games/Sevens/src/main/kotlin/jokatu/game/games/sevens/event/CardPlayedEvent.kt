package jokatu.game.games.sevens.event

import jokatu.game.cards.Card
import jokatu.game.event.PublicGameEvent
import jokatu.game.games.sevens.player.SevensPlayer

import java.text.MessageFormat

class CardPlayedEvent(override val player: SevensPlayer, val card: Card) : PublicGameEvent, HandChangedEvent {

	override val message: String = MessageFormat.format("{0} played {1}", player, card.label)
}
