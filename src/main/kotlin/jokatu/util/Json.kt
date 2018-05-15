package jokatu.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.converter.ConverterFactory
import java.io.IOException
import java.util.*

/**
 * A utility class for handling JSON.
 */
object Json {

	private val OBJECT_MAPPER = ObjectMapper()

	@Throws(JsonProcessingException::class)
	// This is called from the Groovy Templates.
	fun serialise(`object`: Any?): String {
		return OBJECT_MAPPER.writeValueAsString(`object`)
	}

	@Throws(JsonProcessingException::class)
	fun serialiseAndEscape(`object`: Any?): String {
		return serialise(`object`).replace("'".toRegex(), "&#39;")
	}

	@Throws(IOException::class)
	// This can be called from the Groovy Templates.
	fun deserialise(json: String): Map<String, Any?> {
		val typeRef = OBJECT_MAPPER.typeFactory.constructMapType(HashMap::class.java, String::class.java, Any::class.java)
		return OBJECT_MAPPER.readValue(json, typeRef)
	}

	class JacksonConverterFactory : ConverterFactory<String, Any?> {
		override fun <T> getConverter(targetType: Class<T>): Converter<String, T> {
			return Converter { source ->
				try {
					return@Converter OBJECT_MAPPER.readValue<T>(source.toByteArray(), targetType)
				} catch (e: IOException) {
					throw RuntimeException(e)
				}
			}
		}
	}
}
