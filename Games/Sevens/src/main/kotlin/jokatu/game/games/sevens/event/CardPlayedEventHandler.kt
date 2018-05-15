package jokatu.game.games.sevens.event

import jokatu.game.event.AbstractEventHandler
import jokatu.game.games.sevens.game.SevensGame
import org.springframework.stereotype.Component
import java.text.MessageFormat.format

/**
 * @author Steven Weston
 */
@Component
class CardPlayedEventHandler : AbstractEventHandler<SevensGame, CardPlayedEvent>() {

	override val eventClass: Class<CardPlayedEvent>
		get() = CardPlayedEvent::class.java

	override val gameClass: Class<SevensGame>
		get() = SevensGame::class.java

	override fun handleCastGameAndEvent(game: SevensGame, event: CardPlayedEvent) {
		val suit = event.card.suit
		sender.send(
				format("/topic/substatus.game.{0}.{1}", game.identifier, suit),
				game.getCardsOfSuitPlayed(suit)
		)
	}
}
