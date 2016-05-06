package jokatu.components.controllers.game;

import jokatu.components.stores.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class MessageHistoryController {

	private final MessageRepository messageRepository;

	@Autowired
	public MessageHistoryController(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@RequestMapping(value = "/messages", method = GET)
	List<Message> getMessages(@RequestParam("destination") String destination, Principal principal) {
		if (destination.startsWith("/user")) {
			return messageRepository.getMessageHistoryForUser(principal.getName(), destination.substring(5));
		} else {
			return messageRepository.getMessageHistory(destination);
		}
	}

	@RequestMapping(value = "/last_message", method = GET)
	Message getLastMessage(@RequestParam("destination") String destination, Principal principal) {
		if (destination.startsWith("/user")) {
			return messageRepository.getLastMessageForUser(principal.getName(), destination.substring(5));
		} else {
			return messageRepository.getLastMessage(destination);
		}
	}
}
