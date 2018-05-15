package jokatu.components.stores

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

	override fun store(destination: String, message: Message<*>) {
		// This uses ArrayLists for the values, which aren't thread safe, but the chances of two messages for the same
		// destination being sent at the same time are low enough to be ignored for this cheap implementation of
		// AbstractMessageStore.
		MapUtils.updateListBasedMap<String, Message<*>>(messagesPerDestination, destination, message)
	}

	override fun getMessageHistory(destination: String): List<Message<*>> {
		return messagesPerDestination.getOrDefault(destination, emptyList())
	}
}
