package jokatu.components.controllers.game

import jokatu.components.stomp.UnknownDestination
import jokatu.components.stores.MessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.Message
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.GET
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

/**
 * A REST controller for getting the history and last message sent to a STOMP channel.
 */
@RestController
class MessageHistoryController @Autowired constructor(private val messageRepository: MessageRepository) {

	@RequestMapping(value = ["/messages"], method = [GET])
	internal fun getMessages(@RequestParam("destination") destination: String, principal: Principal): List<Message<*>> {
		return if (destination.startsWith("/user")) {
			messageRepository.getMessageHistoryForUser(principal.name, UnknownDestination(destination.substring(5)))
		} else {
			messageRepository.getMessageHistory(UnknownDestination(destination))
		}
	}

	@RequestMapping(value = ["/last_message"], method = [GET])
	internal fun getLastMessage(@RequestParam("destination") destination: String, principal: Principal): Message<*>? {
		return if (destination.startsWith("/user")) {
			messageRepository.getLastMessageForUser(principal.name, UnknownDestination(destination.substring(5)))
		} else {
			messageRepository.getLastMessage(UnknownDestination(destination))
		}
	}
}
