package jokatu.components.stores

import jokatu.components.stomp.Destination
import org.springframework.messaging.Message

/**
 * A place where messages can be stored.
 */
interface MessageStorer {

	fun <T: Any> store(destination: Destination<T>, message: Message<T>)

	fun <T: Any> storeForUser(user: String, destination: Destination<T>, message: Message<T>)
}
