package jokatu.components.stores;

import ophelia.util.MapUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An {@link AbstractMessageStore} that stores messages in a {@link ConcurrentHashMap} that is keyed by channel.
 */
@Component
public class HashMapMessageStore extends AbstractMessageStore {

	private final ConcurrentHashMap<String, List<Message>> messagesPerDestination = new ConcurrentHashMap<>();

	@Override
	public void store(@NotNull String destination, @NotNull Message message) {
		// This uses ArrayLists for the values, which aren't thread safe, but the chances of two messages for the same
		// destination being sent at the same time are low enough to be ignored for this cheap implementation of
		// AbstractMessageStore.
		MapUtils.updateListBasedMap(messagesPerDestination, destination, message);
	}

	@NotNull
	@Override
	public List<Message> getMessageHistory(@NotNull String destination) {
		return messagesPerDestination.getOrDefault(destination, Collections.emptyList());
	}
}
