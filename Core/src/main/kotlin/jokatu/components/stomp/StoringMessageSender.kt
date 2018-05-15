package jokatu.components.stomp

import jokatu.components.stores.MessageStorer
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
	fun sendToUser(user: String, destination: String, payload: Any) {
		sendMessageToUser(user, destination, GenericMessage(payload))
	}

	@Throws(MessagingException::class)
	fun sendMessageToUser(user: String, destination: String, message: Message<*>) {
		messageStore.storeForUser(user, destination, message)
		simpMessagingTemplate.convertAndSendToUser(user, destination, message.payload, message.headers)
	}

	@Throws(MessagingException::class)
	fun send(destination: String, payload: Any) {
		sendMessage(destination, GenericMessage(payload))
	}

	@Throws(MessagingException::class)
	fun sendMessage(destination: String, message: Message<*>) {
		messageStore.store(destination, message)
		simpMessagingTemplate.convertAndSend(destination, message.payload, message.headers)
	}
}
