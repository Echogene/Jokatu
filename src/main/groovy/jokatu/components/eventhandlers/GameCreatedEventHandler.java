package jokatu.components.eventhandlers;

import jokatu.components.config.FactoryConfiguration.GameFactories;
import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.Game;
import jokatu.game.GameFactory;
import jokatu.game.GameID;
import jokatu.game.event.EventHandler;
import jokatu.game.event.GameEvent;
import jokatu.game.games.gameofgames.event.GameCreatedEvent;
import ophelia.util.MapUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.text.MessageFormat.format;


@Component
public class GameCreatedEventHandler extends EventHandler<GameCreatedEvent> {

	@Autowired
	private GameFactories gameFactories;

	@Autowired
	private StoringMessageSender messageSender;

	@Autowired
	private ApplicationContext applicationContext;

	// todo: move this map to its own bean
	private final Map<GameID, List<GameEntry>> entries = new HashMap<>();

	private Collection<EventHandler> eventHandlers;

	@PostConstruct
	public void wireEventListeners() {
		eventHandlers = applicationContext.getBeansOfType(EventHandler.class).values();
	}

	@NotNull
	@Override
	protected Class<GameCreatedEvent> getEventClass() {
		return GameCreatedEvent.class;
	}

	@Override
	protected void handleCastEvent(@NotNull Game gameOfGames, @NotNull GameCreatedEvent gameCreatedEvent) {
		Game<?> game = createGame(gameCreatedEvent.getGameName(), gameCreatedEvent.getPlayer().getName());

		GameID id = gameOfGames.getIdentifier();
		MapUtils.updateListBasedMap(entries, id, new GameEntry(game));

		messageSender.send(
				format("/topic/games.game.{0}", id),
				entries.get(id)
		);
	}

	public Game<?> createGame(String gameName, String playerNawe) {
		GameFactory factory = gameFactories.getFactory(gameName);

		Game<?> game = factory.produceGame(playerNawe);

		game.observe(event -> sendEvent(game, event));

		// Now we are observing events, start the game.
		game.advanceStage();

		return game;
	}

	private void sendEvent(@NotNull Game game, @NotNull GameEvent event) {
		eventHandlers.forEach(eventHandler -> eventHandler.handle(game, event));
	}

	private class GameEntry {
		private final GameID gameId;
		private final String gameName;

		GameEntry(Game<?> game) {
			this.gameId = game.getIdentifier();
			this.gameName = game.getGameName();
		}

		public GameID getGameId() {
			return gameId;
		}

		public String getGameName() {
			return gameName;
		}
	}
}
