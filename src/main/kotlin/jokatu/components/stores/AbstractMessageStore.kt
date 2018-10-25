package jokatu.components.stores

import jokatu.components.stomp.Destination
import ophelia.util.ListUtils
import org.springframework.messaging.Message
import org.springframework.util.StringUtils

/**
 * An abstract implementation of both [MessageStorer] and [MessageRepository].  How the messages are stored
 * is delegated to the subclasses of this.
 */
abstract class AbstractMessageStore : MessageStorer, MessageRepository {

	override fun <T: Any> storeForUser(user: String, destination: Destination<T>, message: Message<T>) {
		store(getUserDestination(user, destination), message)
	}

	private fun <T: Any> getUserDestination(user: String, destination: Destination<T>): Destination<T> {
		// This doesn't need to be the actual destination used by STOMP, just a key to uniquely identify the user's
		// destination.
		return UserDestination(user, destination)
	}

	override fun <T: Any> getMessageHistoryForUser(user: String, destination: Destination<T>): List<Message<T>> {
		return getMessageHistory(getUserDestination(user, destination))
	}

	override fun <T: Any> getLastMessage(destination: Destination<T>): Message<T>? {
		return ListUtils.maybeLast(getMessageHistory(destination))
				.returnOnSuccess()
				.nullOnFailure()
	}

	override fun <T: Any> getLastMessageForUser(user: String, destination: Destination<T>): Message<T>? {
		return ListUtils.maybeLast(getMessageHistoryForUser(user, destination))
				.returnOnSuccess()
				.nullOnFailure()
	}
}

class UserDestination<T: Any>(user: String, destination: Destination<T>): Destination<T>() {
	override val destination = StringUtils.replace(user, "/", "%2F") + "/" + destination
}
