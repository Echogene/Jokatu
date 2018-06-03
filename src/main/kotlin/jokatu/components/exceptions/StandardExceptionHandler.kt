package jokatu.components.exceptions

import jokatu.components.controllers.game.GameController
import jokatu.components.stomp.StoringMessageSender
import jokatu.stomp.SendErrorMessage
import jokatu.stomp.SubscriptionErrorMessage
import ophelia.util.MapUtils
import org.slf4j.getLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.Message
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ErrorMessage
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Component
import java.security.Principal
import java.util.regex.Pattern

@Component
class StandardExceptionHandler @Autowired constructor(private val sender: StoringMessageSender) {

	fun handleMessageException(e: Exception, originalMessage: Message<*>, principal: Principal) {
		val accessor = StompHeaderAccessor.wrap(originalMessage)
		val destination = accessor.destination
		val matcher = GAME_CHANNEL_PATTERN.matcher(destination)
		if (matcher.matches()) {
			val errorMessage = getErrorMessage(e, accessor)
			val errorDestination = "/topic/errors.game." + matcher.group(1)
			try {
				sender.sendMessageToUser(principal.name, errorDestination, errorMessage)
			} catch (f: Exception) {
				sender.sendMessageToUser(principal.name, errorDestination, getBackupErrorMessage(e, accessor))
			}
		}
		log.error(
				"Exception occurred when receiving message\n${accessor.getDetailedLogMessage(originalMessage.payload)}",
				e
		)
	}

	private fun getErrorMessage(e: Throwable, accessor: StompHeaderAccessor): ErrorMessage {
		return when (accessor.command) {
			StompCommand.SUBSCRIBE -> {
				val subscribeId = accessor.getNativeHeader("id")[0]
				SubscriptionErrorMessage(e, subscribeId)
			}
			StompCommand.SEND -> {
				val sendReceipt = accessor.getNativeHeader("receipt")[0]
				SendErrorMessage(e, sendReceipt)
			}
			else -> ErrorMessage(e)
		}
	}

	private fun getBackupErrorMessage(e: Throwable, accessor: StompHeaderAccessor): Message<*> {
		return when (accessor.command) {
			StompCommand.SUBSCRIBE -> {
				val subscribeId = accessor.getNativeHeader("id")[0]
				GenericMessage<String>(e.message, MapUtils.createMap<String, Any>("subscribe-id", subscribeId))
			}
			StompCommand.SEND -> {
				val sendReceipt = accessor.getNativeHeader("receipt")[0]
				GenericMessage<String>(e.message, MapUtils.createMap<String, Any>("receipt-id", sendReceipt))
			}
			else -> GenericMessage<String>(e.message)
		}
	}

	companion object {
		private val log = getLogger(GameController::class)

		private val GAME_CHANNEL_PATTERN = Pattern.compile(".*\\.game\\.(\\d+).*")
	}
}
