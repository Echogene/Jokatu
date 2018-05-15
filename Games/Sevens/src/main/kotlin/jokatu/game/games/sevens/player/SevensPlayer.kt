package jokatu.game.games.sevens.player

import jokatu.game.cards.Card
import jokatu.game.event.GameEvent
import jokatu.game.games.sevens.event.CardDrawnEvent
import jokatu.game.games.sevens.event.CardPlayedEvent
import jokatu.game.player.Player
import ophelia.event.observable.AbstractSynchronousObservable
import java.util.*

class SevensPlayer(override val name: String) : AbstractSynchronousObservable<GameEvent>(), Player {
	val hand: MutableList<Card> = ArrayList()

	fun drawCard(card: Card) {
		hand.add(card)
		fireEvent(CardDrawnEvent(this, card))
	}

	fun playCard(card: Card) {
		val removed = hand.remove(card)
		if (removed) {
			fireEvent(CardPlayedEvent(this, card))
		}
	}

	override fun toString(): String {
		return name
	}
}
