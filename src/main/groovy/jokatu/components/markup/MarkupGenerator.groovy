package jokatu.components.markup

import jokatu.components.stores.MessageRepository
import jokatu.util.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MarkupGenerator {

	@Autowired
	MessageRepository messageRepository

	String bindHistory(Map properties) {
		String destination = properties.destination

		def initialData = Json.serialise(messageRepository.getMessageHistory(destination));

		def tag = properties.remove("tag")

		return "<${tag} ${properties.inject("", {s, k, v -> "${s}${k}='${v}' "})} data-initial='${initialData}'></${tag}>"
	}

	String bindUserHistory(Map properties) {
		String destination = properties.destination.replaceFirst("^/user", "")

		String username = properties.remove("user")
		def initialData = Json.serialise(messageRepository.getMessageHistoryForUser(username, destination));

		def tag = properties.remove("tag")

		return "<${tag} ${properties.inject("", {s, k, v -> "${s}${k}='${v}' "})} data-initial='${initialData}'></${tag}>"
	}

	String bindLast(Map properties) {
		String destination = properties.destination

		def initialData = Json.serialise(messageRepository.getLastMessage(destination));

		def tag = properties.remove("tag")

		return "<${tag} ${properties.inject("", {s, k, v -> "${s}${k}='${v}' "})} data-initial='${initialData}'></${tag}>"
	}

	String bindUserLast(Map properties) {
		String destination = properties.destination.replaceFirst("^/user", "")

		String username = properties.remove("user")
		def initialData = Json.serialise(messageRepository.getLastMessageForUser(username, destination));

		def tag = properties.remove("tag")

		return "<${tag} ${properties.inject("", {s, k, v -> "${s}${k}='${v}' "})} data-initial='${initialData}'></${tag}>"
	}
}
