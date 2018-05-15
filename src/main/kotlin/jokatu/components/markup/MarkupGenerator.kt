package jokatu.components.markup

import com.fasterxml.jackson.core.JsonProcessingException
import jokatu.components.stores.MessageRepository
import jokatu.util.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Suppress("unused")
@Component
class MarkupGenerator {

	@Autowired
	internal lateinit var messageRepository: MessageRepository

	@Throws(JsonProcessingException::class)
	fun bindHistory(properties: MutableMap<*, *>): String {
		return generateTagMarkup(properties, getHistory(properties))
	}

	@Throws(JsonProcessingException::class)
	fun bindUserHistory(properties: MutableMap<*, *>): String {
		return generateTagMarkup(properties, getUserHistory(properties))
	}

	@Throws(JsonProcessingException::class)
	fun bindLast(properties: MutableMap<*, *>): String {
		return generateTagMarkup(properties, getLast(properties))
	}

	@Throws(JsonProcessingException::class)
	fun bindLastStart(properties: MutableMap<*, *>): String {
		return generateStartTag(properties.remove("tag").toString(), properties, getLast(properties))
	}

	@Throws(JsonProcessingException::class)
	fun bindUserLast(properties: MutableMap<*, *>): String {
		return generateTagMarkup(properties, getUserLast(properties))
	}

	@Throws(JsonProcessingException::class)
	fun bindUserLastStart(properties: MutableMap<*, *>): String {
		return generateStartTag(properties.remove("tag").toString(), properties, getUserLast(properties))
	}

	@Throws(JsonProcessingException::class)
	fun getHistory(properties: Map<*, *>): String {
		val destination = properties["destination"].toString()

		return Json.serialiseAndEscape(messageRepository.getMessageHistory(destination))
	}

	@Throws(JsonProcessingException::class)
	fun getUserHistory(properties: MutableMap<*, *>): String {
		val destination = (properties["destination"].toString()).replaceFirst("^/user".toRegex(), "")

		val username = properties.remove("user").toString()
		return Json.serialiseAndEscape(messageRepository.getMessageHistoryForUser(username, destination))
	}

	@Throws(JsonProcessingException::class)
	fun getLast(properties: Map<*, *>): String {
		val destination = properties["destination"].toString()

		return Json.serialiseAndEscape(messageRepository.getLastMessage(destination))
	}

	@Throws(JsonProcessingException::class)
	fun getUserLast(properties: MutableMap<*, *>): String {
		val destination = (properties["destination"].toString()).replaceFirst("^/user".toRegex(), "")

		val username = properties.remove("user").toString()
		return Json.serialiseAndEscape(messageRepository.getLastMessageForUser(username, destination))
	}

	fun generateTagMarkup(properties: MutableMap<*, *>, initialData: String): String {
		val tag = properties.remove("tag").toString()

		return generateStartTag(tag, properties, initialData) + generateEndTag(tag)
	}

	fun generateStartTag(tag: String, properties: Map<*, *>, initialData: String): String {
		val propertiesString = properties.entries.stream()
				.map { entry -> entry.key.toString() + "='" + entry.value + "'" }
				.collect(Collectors.joining(" "))
		return "<$tag $propertiesString data-initial='$initialData'>"
	}

	fun generateEndTag(tag: String): String {
		return "</$tag>"
	}
}
