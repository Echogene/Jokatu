package jokatu.game.games.sevens.event

import jokatu.components.stomp.Topic
import jokatu.game.cards.Card
import jokatu.game.cards.Suit
import jokatu.game.event.AbstractEventHandler
import jokatu.game.games.sevens.game.SevensGame
import org.springframework.stereotype.Component

/**
 * @author Steven Weston
 */
@Component
class CardPlayedEventHandler : AbstractEventHandler<SevensGame, CardPlayedEvent>(SevensGame::class, CardPlayedEvent::class) {
	override fun handleCastGameAndEvent(game: SevensGame, event: CardPlayedEvent) {
		val suit = event.card.suit
		sender.send(Suit(game, suit), game.getCardsOfSuitPlayed(suit))
	}
}

private class Suit(game: SevensGame, suit: Suit): Topic<Set<Card>>("substatus.game.${game.identifier}.$suit")