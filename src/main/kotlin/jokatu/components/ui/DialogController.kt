package jokatu.components.ui

import jokatu.components.exceptions.StandardExceptionHandler
import jokatu.game.GameID
import jokatu.game.exception.GameException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller
import java.security.Principal

@Controller
class DialogController @Autowired constructor(
		private val dialogResponder: DialogResponder,
		private val standardExceptionHandler: StandardExceptionHandler
) {

	@MessageMapping("/topic/input.dialog.game.{identity}")
	@Throws(GameException::class)
	fun input(@DestinationVariable("identity") identity: GameID, @Payload json: MutableMap<String, Any>, principal: Principal) {

		if (!json.containsKey(DialogResponder.DIALOG_ID)) {
			throw GameException(
					identity,
					"The key ''{0}'' was missing in the dialog response {1}.",
					DialogResponder.DIALOG_ID,
					json
			)
		}
		val dialogId = json.remove(DialogResponder.DIALOG_ID)
		if (dialogId !is String) {
			throw GameException(
					identity,
					"The value ''{0}'' for the key ''{1}'' was not a string in the dialog response {2}.",
					dialogId,
					DialogResponder.DIALOG_ID,
					json
			)
		}
		dialogResponder.respondToDialog(DialogResponder.DialogID(identity, dialogId), principal.name, json)
	}

	@MessageExceptionHandler(Exception::class)
	internal fun handleException(e: Exception, originalMessage: Message<*>, principal: Principal) {
		standardExceptionHandler.handleMessageException(e, originalMessage, principal)
	}
}
