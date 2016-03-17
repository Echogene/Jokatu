package jokatu.components.stores;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A place whence messages can be retrieved.
 */
public interface MessageRepository {
	@NotNull
	List<Object> getMessageHistory(@NotNull String destination);

	@NotNull
	List<Object> getMessageHistoryForUser(@NotNull String user, @NotNull String destination);

	@Nullable
	Object getLastMessage(@NotNull String destination);

	@NotNull
	Object getLastMessageForUser(@NotNull String user, @NotNull String destination);
}
