package jokatu.components.stores;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
