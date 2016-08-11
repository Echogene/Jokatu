package jokatu.components.exceptions;

import jokatu.components.controllers.game.GameController;
import jokatu.components.stomp.StoringMessageSender;
import jokatu.stomp.SendErrorMessage;
import jokatu.stomp.SubscriptionErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GameController.class);

	private static final Pattern GAME_CHANNEL_PATTERN = Pattern.compile(".*\\.game\\.(\\d+).*");

	private final StoringMessageSender sender;

	@Autowired
	public ExceptionHandler(StoringMessageSender sender) {
		this.sender = sender;
	}

	public void handleException(Exception e, Message originalMessage, Principal principal) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(originalMessage);
		String destination = accessor.getDestination();
		Matcher matcher = GAME_CHANNEL_PATTERN.matcher(destination);
		if (matcher.matches()) {
			ErrorMessage errorMessage = getErrorMessage(e, accessor);
			sender.sendMessageToUser(principal.getName(), "/topic/errors.game." + matcher.group(1), errorMessage);
		}
		log.error(
				MessageFormat.format(
						"Exception occurred when receiving message\n{0}",
						accessor.getDetailedLogMessage(originalMessage.getPayload())
				),
				e
		);
	}

	private ErrorMessage getErrorMessage(Throwable e, StompHeaderAccessor accessor) {
		switch (accessor.getCommand()) {
			case SUBSCRIBE:
				String subscribeId = accessor.getNativeHeader("id").get(0);
				return new SubscriptionErrorMessage(e, subscribeId);
			case SEND:
				String sendReceipt = accessor.getNativeHeader("receipt").get(0);
				return new SendErrorMessage(e, sendReceipt);
			default:
				return new ErrorMessage(e);
		}
	}
}
