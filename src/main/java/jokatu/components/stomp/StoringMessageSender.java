package jokatu.components.stomp;

import jokatu.components.stores.MessageStorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StoringMessageSender {

	private final MessageStorer messageStore;
	private final SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	public StoringMessageSender(MessageStorer messageStore, SimpMessagingTemplate simpMessagingTemplate) {
		this.messageStore = messageStore;
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	public void sendToUser(String user, String destination, Object payload) throws MessagingException {
		messageStore.storeForUser(user, destination, payload);
		simpMessagingTemplate.convertAndSendToUser(user, destination, payload);
	}

	public void send(String destination, Object payload) throws MessagingException {
		messageStore.store(destination, payload);
		simpMessagingTemplate.convertAndSend(destination, payload);
	}

	public void sendToUser(String user, String destination, Object payload, Map<String, Object> errorHeaders) {
		messageStore.storeForUser(user, destination, payload);
		simpMessagingTemplate.convertAndSendToUser(user, destination, payload, errorHeaders);
	}
}
