	package jokatu.components.ui;

import jokatu.components.exceptions.StandardExceptionHandler;
import jokatu.game.GameID;
import jokatu.game.exception.GameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

import static jokatu.components.ui.DialogResponder.*;

@Controller
public class DialogController {

	private final DialogResponder dialogResponder;
	private final StandardExceptionHandler standardExceptionHandler;

	@Autowired
	public DialogController(DialogResponder dialogResponder, StandardExceptionHandler standardExceptionHandler) {
		this.dialogResponder = dialogResponder;
		this.standardExceptionHandler = standardExceptionHandler;
	}

	@MessageMapping("/topic/input.dialog.game.{identity}")
	public void input(@DestinationVariable("identity") GameID identity, @Payload Map<String, Object> json, Principal principal)
			throws GameException {

		if (!json.containsKey(DIALOG_ID)) {
			throw new GameException(
					identity,
					"The key ''{0}'' was missing in the dialog response {1}.",
					DIALOG_ID,
					json
			);
		}
		Object dialogId = json.remove(DIALOG_ID);
		if (!(dialogId instanceof String)) {
			throw new GameException(
					identity,
					"The value ''{0}'' for the key ''{1}'' was not a string in the dialog response {2}.",
					dialogId,
					DIALOG_ID,
					json
			);
		}
		String id = (String) dialogId;
		dialogResponder.respondToDialog(new DialogID(identity, id), principal.getName(), json);
	}

	@MessageExceptionHandler(Exception.class)
	void handleException(Exception e, Message originalMessage, Principal principal) {
		standardExceptionHandler.handleMessageException(e, originalMessage, principal);
	}
}
