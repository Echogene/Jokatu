package jokatu.components.eventhandlers

import jokatu.components.dao.GameDao
import jokatu.game.Game
import jokatu.game.GameID
import jokatu.game.event.SpecificEventHandler
import jokatu.game.joining.PlayerJoinedEvent
import ophelia.util.function.PredicateUtils.not
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import org.springframework.web.socket.messaging.SessionSubscribeEvent
import java.text.MessageFormat.format
import java.util.Collections.emptyMap
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.regex.Pattern
import java.util.stream.Collectors.toSet

/**
 * When a player joins a game, we need to move them from the observers list to the players list.  When a player stops
 * observing/starts to observe a game (by un/subscribing to the [.STATUS_REGEX] destination), the observers list
 * needs to be updated.
 */
@Component
class PlayerJoinEventHandler
@Autowired constructor(
		private val gameDao: GameDao
) : SpecificEventHandler<PlayerJoinedEvent>(), ApplicationListener<ApplicationEvent> {

	private val gameUsers = ConcurrentHashMap<GameID, MutableMap<String, String>>()
	private val gameUpdates = ConcurrentHashMap<GameID, ScheduledFuture<*>>()
	private val sessionGames = ConcurrentHashMap<String, GameID>()

	private val executorService = Executors.newScheduledThreadPool(2)

	override val eventClass: Class<PlayerJoinedEvent>
		get() = PlayerJoinedEvent::class.java

	override fun onApplicationEvent(event: ApplicationEvent) {

		if (event is SessionSubscribeEvent) {
			val accessor = StompHeaderAccessor.wrap(event.message)
			val destination = accessor.destination
			val matcher = STATUS_REGEX.matcher(destination)
			if (matcher.matches()) {
				val gameId = GameID(java.lang.Long.parseLong(matcher.group(1)))
				handleStatusSubscription(accessor, gameId)
			}
		}
		if (event is SessionDisconnectEvent) {
			val accessor = StompHeaderAccessor.wrap(event.message)
			val gameId = sessionGames.remove(accessor.sessionId)
			if (gameId != null) {
				handleSessionDisconnect(accessor, gameId)
			}
		}
	}

	private fun handleSessionDisconnect(accessor: StompHeaderAccessor, gameId: GameID) {
		val sessionUsers = gameUsers[gameId]
		sessionUsers?.remove(accessor.sessionId)

		scheduleUpdate(gameId)
	}

	private fun handleStatusSubscription(accessor: StompHeaderAccessor, gameId: GameID) {
		var users: MutableMap<String, String>? = gameUsers[gameId]
		if (!gameUsers.containsKey(gameId) || users == null) {
			users = ConcurrentHashMap()
			gameUsers[gameId] = users
		}
		users[accessor.sessionId] = accessor.user.name
		sessionGames[accessor.sessionId] = gameId

		scheduleUpdate(gameId)
	}

	private fun scheduleUpdate(gameId: GameID) {
		val userNames = gameUsers.getOrDefault(gameId, emptyMap()).values
		if (gameUpdates.containsKey(gameId)) {
			// There is already a scheduled update for this game.
			return
		}

		// Schedule this in the near future.  If the user refreshes the page, we don't want them to disappear and
		// reappear so soon.
		val future = executorService.schedule({
			gameUpdates.remove(gameId)
			val game = gameDao.read(gameId) ?: return@schedule
			val observers = userNames.stream()
					.filter(not { game.hasPlayer(it) })
					.collect(toSet())
			sender.send(format("/topic/observers.game.{0}", gameId), observers)

			val playerStatuses = game.getPlayers().stream()
					.map({ it.name })
					.map { name -> PlayerStatus(userNames.contains(name), name) }
					.collect(toSet())
			sender.send(format("/topic/players.game.{0}", gameId), playerStatuses)
		}, 500, MILLISECONDS)
		gameUpdates[gameId] = future
	}

	override fun handleCastEvent(game: Game<*>, event: PlayerJoinedEvent) {
		scheduleUpdate(game.identifier)
	}

	// This is converted to JSON using Jackson.
	private class PlayerStatus constructor(val isOnline: Boolean, val name: String)

	companion object {
		private val STATUS_REGEX = Pattern.compile("/topic/observers\\.game\\.(\\d+)")
	}
}
