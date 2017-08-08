package jokatu.components.stores;

import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;

/**
 * A place where messages can be stored.
 */
public interface MessageStorer {

	void store(@NotNull String destination, @NotNull Message message);

	void storeForUser(@NotNull String user, @NotNull String destination, @NotNull Message message);
}
