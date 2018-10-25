package jokatu.game.games.sevens.event

import jokatu.components.stomp.Topic
import jokatu.game.Game
import jokatu.game.cards.Card
import jokatu.game.event.SpecificEventHandler
import jokatu.game.games.sevens.player.SevensPlayer
import org.springframework.stereotype.Component

/**
 * Update the user's hand and inform everyone else that the number of cards in their hand has changed.
 * @author Steven Weston
 */
@Component
class HandChangedEventHandler : SpecificEventHandler<HandChangedEvent>(HandChangedEvent::class) {
	override fun handleCastEvent(game: Game<*>, event: HandChangedEvent) {
		val player = event.player
		sender.sendToUser(player.name, Hand(game), player.hand)

		sender.send(Handcount(game, player), player.hand.size)
	}
}

private class Hand(game: Game<*>): Topic<List<Card>>("hand.game.${game.identifier}")

private class Handcount(game: Game<*>, player: SevensPlayer): Topic<Int>("handcount.game.${game.identifier}.$player")