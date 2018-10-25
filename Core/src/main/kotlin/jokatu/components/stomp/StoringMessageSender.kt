package jokatu.components.stomp

import jokatu.components.stores.MessageStorer
import org.slf4j.getLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.Message
import org.springframework.messaging.MessagingException
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Component

/**
 * Sends messages to STOMP destinations and stores a history of them.
 */
@Component
class StoringMessageSender @Autowired constructor(
		private val messageStore: MessageStorer,
		private val simpMessagingTemplate: SimpMessagingTemplate
) {

	@Throws(MessagingException::class)
	fun <T: Any> sendToUser(user: String, destination: Destination<T>, payload: T) {
		sendMessageToUser(user, destination, GenericMessage(payload))
	}

	@Throws(MessagingException::class)
	fun <T: Any> sendMessageToUser(user: String, destination: Destination<T>, message: Message<T>) {
		log.trace("Sending message '$message' to $destination for $user")
		messageStore.storeForUser(user, destination, message)
		simpMessagingTemplate.convertAndSendToUser(user, destination.toString(), message.payload, message.headers)
	}

	@Throws(MessagingException::class)
	fun <T: Any> send(destination: Destination<T>, payload: T) {
		sendMessage(destination, GenericMessage(payload))
	}

	@Throws(MessagingException::class)
	fun <T: Any> sendMessage(destination: Destination<T>, message: Message<T>) {
		log.trace("Sending message '$message' to $destination")
		messageStore.store(destination, message)
		simpMessagingTemplate.convertAndSend(destination.toString(), message.payload, message.headers)
	}

	companion object {
		private val log = getLogger(StoringMessageSender::class)
	}
}
