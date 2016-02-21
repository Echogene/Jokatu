package jokatu.components.stores;

import org.jetbrains.annotations.NotNull;

public interface MessageStorer {

	void store(@NotNull String destination, @NotNull Object message);

	void storeForUser(@NotNull String user, @NotNull String destination, @NotNull Object message);
}
