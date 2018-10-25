package jokatu.components.stores

import jokatu.components.stomp.Destination
import ophelia.util.MapUtils
import org.springframework.messaging.Message
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

/**
 * An [AbstractMessageStore] that stores messages in a [ConcurrentHashMap] that is keyed by channel.
 */
@Component
class HashMapMessageStore : AbstractMessageStore() {

	private val messagesPerDestination = ConcurrentHashMap<String, List<Message<*>>>()

	override fun <T: Any> store(destination: Destination<T>, message: Message<T>) {
		// This uses ArrayLists for the values, which aren't thread safe, but the chances of two messages for the same
		// destination being sent at the same time are low enough to be ignored for this cheap implementation of
		// AbstractMessageStore.
		MapUtils.updateListBasedMap(messagesPerDestination, destination.toString(), message)
	}

	override fun <T: Any> getMessageHistory(destination: Destination<T>): List<Message<T>> {
		@Suppress("UNCHECKED_CAST")
		return messagesPerDestination.getOrDefault(destination.toString(), emptyList()) as List<Message<T>>
	}
}
