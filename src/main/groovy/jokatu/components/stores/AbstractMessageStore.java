package jokatu.components.stores;

import ophelia.util.ListUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * An abstract implementation of both {@link MessageStorer} and {@link MessageRepository}.  How the messages are stored
 * is delegated to the subclasses of this.
 */
abstract class AbstractMessageStore implements MessageStorer, MessageRepository {

	@Override
	public void storeForUser(@NotNull String user, @NotNull String destination, @NotNull Object message) {
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
	public List<Object> getMessageHistoryForUser(@NotNull String user, @NotNull String destination) {
		return getMessageHistory(getUserDestination(user, destination));
	}

	@Override
	@Nullable
	public Object getLastMessage(@NotNull String destination) {
		return ListUtils.maybeLast(getMessageHistory(destination))
				.returnOnSuccess()
				.nullOnFailure();
	}

	@NotNull
	@Override
	public Object getLastMessageForUser(@NotNull String user, @NotNull String destination) {
		return getMessageHistory(getUserDestination(user, destination));
	}
}
