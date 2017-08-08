package jokatu.components.stomp;

import jokatu.components.stores.MessageStorer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

/**
 * Sends messages to STOMP destinations and stores a history of them.
 */
@Component
public class StoringMessageSender {

	private final MessageStorer messageStore;
	private final SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	public StoringMessageSender(MessageStorer messageStore, SimpMessagingTemplate simpMessagingTemplate) {
		this.messageStore = messageStore;
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	public void sendToUser(@NotNull String user, @NotNull String destination, @NotNull Object payload) throws MessagingException {
		sendMessageToUser(user, destination, new GenericMessage<>(payload));
	}

	public void sendMessageToUser(@NotNull String user, @NotNull String destination, @NotNull Message message) throws MessagingException {
		messageStore.storeForUser(user, destination, message);
		simpMessagingTemplate.convertAndSendToUser(user, destination, message.getPayload(), message.getHeaders());
	}

	public void send(@NotNull String destination, @NotNull Object payload) throws MessagingException {
		sendMessage(destination, new GenericMessage<>(payload));
	}

	public void sendMessage(@NotNull String destination, @NotNull Message message) throws MessagingException {
		messageStore.store(destination, message);
		simpMessagingTemplate.convertAndSend(destination, message.getPayload(), message.getHeaders());
	}
}
