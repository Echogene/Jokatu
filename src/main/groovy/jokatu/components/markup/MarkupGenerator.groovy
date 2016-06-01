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
		return generateTagMarkup(properties, getHistory(properties));
	}

	String bindUserHistory(Map properties) {
		return generateTagMarkup(properties, getUserHistory(properties));
	}

	String bindLast(Map properties) {
		return generateTagMarkup(properties, getLast(properties));
	}

	String bindUserLast(Map properties) {
		return generateTagMarkup(properties, getUserLast(properties));
	}

	String bindUserLastStart(Map properties) {
		return generateStartTag(properties.remove("tag"), properties, getUserLast(properties));
	}

	private String getHistory(Map properties) {
		String destination = properties.destination

		return Json.serialise(messageRepository.getMessageHistory(destination));
	}

	private String getUserHistory(Map properties) {
		String destination = properties.destination.replaceFirst("^/user", "")

		String username = properties.remove("user")
		return Json.serialise(messageRepository.getMessageHistoryForUser(username, destination));
	}

	private String getLast(Map properties) {
		String destination = properties.destination

		return Json.serialise(messageRepository.getLastMessage(destination));
	}

	private String getUserLast(Map properties) {
		String destination = properties.destination.replaceFirst("^/user", "")

		String username = properties.remove("user")
		return Json.serialise(messageRepository.getLastMessageForUser(username, destination));
	}

	private static GString generateTagMarkup(Map properties, initialData) {
		def tag = properties.remove("tag")

		return generateStartTag(tag, properties, initialData) + generateEndTag(tag)
	}

	private static GString generateStartTag(tag, Map properties, initialData) {
		"<${tag} ${properties.inject("", { s, k, v -> "${s}${k}='${v}' " })} data-initial='${initialData}'>"
	}

	private static GString generateEndTag(tag) {
		"</${tag}>"
	}
}
