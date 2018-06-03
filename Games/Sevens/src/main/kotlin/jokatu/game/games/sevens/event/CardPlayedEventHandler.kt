package jokatu.game.games.sevens.event

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
		sender.send(
				"/topic/substatus.game.${game.identifier}.$suit",
				game.getCardsOfSuitPlayed(suit)
		)
	}
}
