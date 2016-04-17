package jokatu.components.stores;

import ophelia.util.ListUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * An abstract implementation of both {@link MessageStorer} and {@link MessageRepository}.  How the messages are stored
 * is delegated to the subclasses of this.
 */
abstract class AbstractMessageStore implements MessageStorer, MessageRepository {

	@Override
	public void storeForUser(@NotNull String user, @NotNull String destination, @NotNull Message message) {
		store(getUserDestination(user, destination), message);
	}

	@NotNull
	private String getUserDestination(@NotNull String user, @NotNull String destination) {
		// This doesn't need to be the actual destination used by STOMP, just a key to uniquely identify the user's
		// destination.
		return StringUtils.replace(user, "/", "%2F") + "/" + destination;
	}

	@NotNull
	@Override
	public List<Message> getMessageHistoryForUser(@NotNull String user, @NotNull String destination) {
		return getMessageHistory(getUserDestination(user, destination));
	}

	@Override
	@Nullable
	public Message getLastMessage(@NotNull String destination) {
		return ListUtils.maybeLast(getMessageHistory(destination))
				.returnOnSuccess()
				.nullOnFailure();
	}

	@Nullable
	@Override
	public Message getLastMessageForUser(@NotNull String user, @NotNull String destination) {
		return ListUtils.maybeLast(getMessageHistoryForUser(user, destination))
				.returnOnSuccess()
				.nullOnFailure();
	}
}
