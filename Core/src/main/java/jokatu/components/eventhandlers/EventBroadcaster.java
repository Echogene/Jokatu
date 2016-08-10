package jokatu.components.eventhandlers;

import jokatu.game.Game;
import jokatu.game.event.EventHandler;
import jokatu.game.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * Broadcasts a game event to all event handlers in the application context.
 */
@Component
public class EventBroadcaster {

	private final ApplicationContext applicationContext;

	private Collection<EventHandler> eventHandlers;

	@Autowired
	public EventBroadcaster(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@PostConstruct
	public void wireEventListeners() {
		eventHandlers = applicationContext.getBeansOfType(EventHandler.class).values();
	}

	public void broadcast(@NotNull Game game, @NotNull GameEvent event) {
		eventHandlers.forEach(eventHandler -> eventHandler.handle(game, event));
	}
}
