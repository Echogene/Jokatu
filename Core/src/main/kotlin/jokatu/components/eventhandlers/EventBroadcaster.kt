package jokatu.components.eventhandlers

import jokatu.game.Game
import jokatu.game.event.EventHandler
import jokatu.game.event.GameEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBeansOfType
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

/**
 * Broadcasts a game event to all event handlers in the application context.
 */
@Component
class EventBroadcaster @Autowired constructor(private val applicationContext: ApplicationContext) {

	private lateinit var eventHandlers: Collection<EventHandler>

	@PostConstruct
	fun wireEventListeners() {
		eventHandlers = applicationContext.getBeansOfType(EventHandler::class)
	}

	/**
	 * Broadcast the given [event][GameEvent] for the given [game][Game] to all [event handler beans][EventHandler].
	 */
	fun broadcast(game: Game<*>, event: GameEvent) {
		eventHandlers.forEach { eventHandler -> eventHandler.handle(game, event) }
	}
}
