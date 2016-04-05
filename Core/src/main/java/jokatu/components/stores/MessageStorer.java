package jokatu.components.stores;

import org.jetbrains.annotations.NotNull;

/**
 * A place where messages can be stored.
 */
public interface MessageStorer {

	void store(@NotNull String destination, @NotNull Object message);

	void storeForUser(@NotNull String user, @NotNull String destination, @NotNull Object message);
}
