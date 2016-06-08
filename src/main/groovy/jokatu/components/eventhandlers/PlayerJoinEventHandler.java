package jokatu.components.eventhandlers;

import jokatu.components.dao.GameDao;
import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.event.SpecificEventHandler;
import jokatu.game.joining.PlayerJoinedEvent;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.text.MessageFormat.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.stream.Collectors.toSet;
import static ophelia.util.FunctionUtils.not;

@Component
public class PlayerJoinEventHandler extends SpecificEventHandler<PlayerJoinedEvent> implements ApplicationListener {

	private static final Pattern STATUS_REGEX = Pattern.compile("/topic/observers\\.game\\.(\\d+)");

	private final Map<GameID, Map<String, String>> gameUsers = new ConcurrentHashMap<>();
	private final Map<GameID, ScheduledFuture<?>> gameUpdates = new ConcurrentHashMap<>();
	private final Map<String, GameID> sessionGames = new ConcurrentHashMap<>();

	private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

	private final GameDao gameDao;

	@Autowired
	public PlayerJoinEventHandler(GameDao gameDao) {
		this.gameDao = gameDao;
	}

	@NotNull
	@Override
	protected Class<PlayerJoinedEvent> getEventClass() {
		return PlayerJoinedEvent.class;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {

		if (event instanceof SessionSubscribeEvent) {
			SessionSubscribeEvent e = (SessionSubscribeEvent) event;
			StompHeaderAccessor accessor = StompHeaderAccessor.wrap(e.getMessage());
			String destination = accessor.getDestination();
			Matcher matcher = STATUS_REGEX.matcher(destination);
			if (matcher.matches()) {
				GameID gameId = new GameID(Long.parseLong(matcher.group(1)));
				handleStatusSubscription(accessor, gameId);
			}
		}
		if (event instanceof SessionDisconnectEvent) {
			SessionDisconnectEvent e = (SessionDisconnectEvent) event;
			StompHeaderAccessor accessor = StompHeaderAccessor.wrap(e.getMessage());
			GameID gameId = sessionGames.remove(accessor.getSessionId());
			if (gameId != null) {
				handleSessionDisconnect(accessor, gameId);
			}
		}
	}

	private void handleSessionDisconnect(StompHeaderAccessor accessor, GameID gameId) {
		Map<String, String> sessionUsers = gameUsers.get(gameId);
		sessionUsers.remove(accessor.getSessionId());

		scheduleUpdate(gameId);
	}

	private void handleStatusSubscription(StompHeaderAccessor accessor, GameID gameId) {
		if (!gameUsers.containsKey(gameId)) {
			gameUsers.put(gameId, new ConcurrentHashMap<>());
		}
		gameUsers.get(gameId).put(accessor.getSessionId(), accessor.getUser().getName());
		sessionGames.put(accessor.getSessionId(), gameId);

		scheduleUpdate(gameId);
	}

	private void scheduleUpdate(GameID gameId) {
		Collection<String> userNames = gameUsers.get(gameId).values();
		if (gameUpdates.containsKey(gameId)) {
			// There is already a scheduled update for this game.
			return;
		}

		// Schedule this in the near future.  If the user refreshes the page, we don't want them to disappear and
		// reappear so soon.
		ScheduledFuture<?> future = executorService.schedule(() -> {
			gameUpdates.remove(gameId);
			Game<? extends Player> game = gameDao.read(gameId);
			if (game == null) {
				return;
			}
			Set<String> observers = userNames.stream()
					.filter(not(game::hasPlayer))
					.collect(toSet());
			sender.send(format("/topic/observers.game.{0}", gameId), observers);

			Set<PlayerStatus> playerStatuses = game.getPlayers().stream()
					.map(Player::getName)
					.map(name -> new PlayerStatus(userNames.contains(name), name))
					.collect(toSet());
			sender.send(format("/topic/players.game.{0}", gameId), playerStatuses);
		}, 500, MILLISECONDS);
		gameUpdates.put(gameId, future);
	}

	@Override
	protected void handleCastEvent(@NotNull Game<?> game, @NotNull PlayerJoinedEvent event) {
		scheduleUpdate(game.getIdentifier());
	}

	@SuppressWarnings("unused") // This is converted to JSON using Jackson.
	private static class PlayerStatus {
		private final boolean online;
		private final String name;

		private PlayerStatus(boolean online, String name) {
			this.online = online;
			this.name = name;
		}

		public boolean isOnline() {
			return online;
		}

		public String getName() {
			return name;
		}
	}
}
