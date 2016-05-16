package jokatu.components.eventhandlers;

import jokatu.components.config.FactoryConfiguration.GameFactories;
import jokatu.game.Game;
import jokatu.game.GameFactory;
import jokatu.game.event.AbstractEventHandler;
import jokatu.game.event.EventHandler;
import jokatu.game.event.GameEvent;
import jokatu.game.games.gameofgames.event.GameCreatedEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;


@Component
public class GameCreatedEventHandler extends AbstractEventHandler<GameCreatedEvent> {

	@Autowired
	private GameFactories gameFactories;

	@Autowired
	private ApplicationContext applicationContext;

	private Collection<EventHandler> eventHandlers;

	@PostConstruct
	public void wireEventListeners() {
		eventHandlers = applicationContext.getBeansOfType(EventHandler.class).values();
	}

	@Override
	protected Class<GameCreatedEvent> handles() {
		return GameCreatedEvent.class;
	}

	@Override
	protected void handleCastEvent(Game gameOfGames, GameCreatedEvent gameCreatedEvent) {
		createGame(gameCreatedEvent.getGameName(), gameCreatedEvent.getPlayer().getName());
	}

	public void createGame(String gameName, String playerNawe) {
		GameFactory factory = gameFactories.getFactory(gameName);

		Game<?> game = factory.produceGame(playerNawe);

		game.observe(event -> sendEvent(game, event));

		// Now we are observing events, start the game.
		game.advanceStage();
	}

	private void sendEvent(@NotNull Game game, @NotNull GameEvent event) {
		eventHandlers.forEach(eventHandler -> eventHandler.handle(game, event));
	}
}
