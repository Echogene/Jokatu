package jokatu.components.stores

import org.springframework.messaging.Message

/**
 * A place whence messages can be retrieved.
 */
interface MessageRepository {
	fun getMessageHistory(destination: String): List<Message<*>>

	fun getMessageHistoryForUser(user: String, destination: String): List<Message<*>>

	fun getLastMessage(destination: String): Message<*>?

	fun getLastMessageForUser(user: String, destination: String): Message<*>?
}
