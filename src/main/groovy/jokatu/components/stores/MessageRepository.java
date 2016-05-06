package jokatu.components.stores;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.messaging.Message;

import java.util.List;

/**
 * A place whence messages can be retrieved.
 */
public interface MessageRepository {
	@NotNull
	List<Message> getMessageHistory(@NotNull String destination);

	@NotNull
	List<Message> getMessageHistoryForUser(@NotNull String user, @NotNull String destination);

	@Nullable
	Message getLastMessage(@NotNull String destination);

	@Nullable
	Message getLastMessageForUser(@NotNull String user, @NotNull String destination);
}
