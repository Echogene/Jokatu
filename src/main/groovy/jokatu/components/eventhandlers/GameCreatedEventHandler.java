package jokatu.components.eventhandlers;

import jokatu.components.config.FactoryConfiguration.GameFactories;
import jokatu.game.Game;
import jokatu.game.GameFactory;
import jokatu.game.GameID;
import jokatu.game.event.AnyGameEventHandler;
import jokatu.game.games.gameofgames.event.GameCreatedEvent;
import ophelia.util.MapUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.text.MessageFormat.format;


@Component
public class GameCreatedEventHandler extends AnyGameEventHandler<GameCreatedEvent> {

	private final GameFactories gameFactories;
	private final EventBroadcaster eventBroadcaster;

	// todo: move this map to its own bean
	private final Map<GameID, List<GameEntry>> entries = new HashMap<>();

	@Autowired
	public GameCreatedEventHandler(GameFactories gameFactories, EventBroadcaster eventBroadcaster) {
		this.gameFactories = gameFactories;
		this.eventBroadcaster = eventBroadcaster;
	}

	@NotNull
	@Override
	protected Class<GameCreatedEvent> getEventClass() {
		return GameCreatedEvent.class;
	}

	@Override
	protected void handleCastGameAndEvent(@NotNull Game gameOfGames, @NotNull GameCreatedEvent gameCreatedEvent) {
		Game<?> game = createGame(gameCreatedEvent.getGameName(), gameCreatedEvent.getPlayer().getName());

		GameID id = gameOfGames.getIdentifier();
		MapUtils.updateListBasedMap(entries, id, new GameEntry(game));

		sender.send(
				format("/topic/games.game.{0}", id),
				entries.get(id)
		);
	}

	private Game<?> createGame(@NotNull String gameName, @NotNull String playerName) {
		GameFactory factory = gameFactories.getFactory(gameName);

		Game<?> game = factory.produceGame(playerName);

		game.observe(event -> eventBroadcaster.broadcast(game, event));

		// Now we are observing events, start the game.
		game.advanceStage();

		return game;
	}

	private class GameEntry {
		private final GameID gameId;
		private final String gameName;

		GameEntry(@NotNull Game<?> game) {
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
