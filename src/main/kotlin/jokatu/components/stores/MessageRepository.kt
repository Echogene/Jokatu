package jokatu.components.stores

import jokatu.components.stomp.Destination
import org.springframework.messaging.Message

/**
 * A place whence messages can be retrieved.
 */
interface MessageRepository {
	fun <T: Any> getMessageHistory(destination: Destination<T>): List<Message<T>>

	fun <T: Any> getMessageHistoryForUser(user: String, destination: Destination<T>): List<Message<T>>

	fun <T: Any> getLastMessage(destination: Destination<T>): Message<T>?

	fun <T: Any> getLastMessageForUser(user: String, destination: Destination<T>): Message<T>?
}
