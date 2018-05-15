package jokatu.components.stores

import ophelia.util.ListUtils
import org.springframework.messaging.Message
import org.springframework.util.StringUtils

/**
 * An abstract implementation of both [MessageStorer] and [MessageRepository].  How the messages are stored
 * is delegated to the subclasses of this.
 */
abstract class AbstractMessageStore : MessageStorer, MessageRepository {

	override fun storeForUser(user: String, destination: String, message: Message<*>) {
		store(getUserDestination(user, destination), message)
	}

	private fun getUserDestination(user: String, destination: String): String {
		// This doesn't need to be the actual destination used by STOMP, just a key to uniquely identify the user's
		// destination.
		return StringUtils.replace(user, "/", "%2F") + "/" + destination
	}

	override fun getMessageHistoryForUser(user: String, destination: String): List<Message<*>> {
		return getMessageHistory(getUserDestination(user, destination))
	}

	override fun getLastMessage(destination: String): Message<*>? {
		return ListUtils.maybeLast<Message<*>>(getMessageHistory(destination))
				.returnOnSuccess()
				.nullOnFailure()
	}

	override fun getLastMessageForUser(user: String, destination: String): Message<*>? {
		return ListUtils.maybeLast<Message<*>>(getMessageHistoryForUser(user, destination))
				.returnOnSuccess()
				.nullOnFailure()
	}
}
