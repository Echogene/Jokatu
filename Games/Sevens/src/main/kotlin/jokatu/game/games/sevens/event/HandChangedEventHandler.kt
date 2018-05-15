package jokatu.game.games.sevens.event

import jokatu.game.Game
import jokatu.game.event.SpecificEventHandler
import org.springframework.stereotype.Component
import java.text.MessageFormat.format

/**
 * Update the user's hand and inform everyone else that the number of cards in their hand has changed.
 * @author Steven Weston
 */
@Component
class HandChangedEventHandler : SpecificEventHandler<HandChangedEvent>() {

	override val eventClass: Class<HandChangedEvent>
		get() = HandChangedEvent::class.java

	override fun handleCastEvent(game: Game<*>, event: HandChangedEvent) {
		val player = event.player
		sender.sendToUser(
				player.name,
				format("/topic/hand.game.{0}", game.identifier),
				player.hand
		)

		sender.send(
				format("/topic/handcount.game.{0}.{1}", game.identifier, player),
				player.hand.size
		)
	}
}
