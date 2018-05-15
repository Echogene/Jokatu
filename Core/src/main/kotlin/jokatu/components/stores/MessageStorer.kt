package jokatu.components.stores

import org.springframework.messaging.Message

/**
 * A place where messages can be stored.
 */
interface MessageStorer {

	fun store(destination: String, message: Message<*>)

	fun storeForUser(user: String, destination: String, message: Message<*>)
}
