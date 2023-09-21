package jokatu.components.config

import jokatu.util.Json
import org.slf4j.getLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.format.support.DefaultFormattingConversionService
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler
import javax.annotation.PostConstruct

/**
 * Configure the conversion service for messages to use the default Jackson converter.
 */
@Configuration
class MessageConverterConfiguration {
	@Autowired
	private lateinit var simpAnnotationMethodMessageHandler: SimpAnnotationMethodMessageHandler

	// This is called by Spring
	@PostConstruct
	fun init() {
		// How about you use the ConversionService that already exists, rather than creating your own, and then this
		// wouldn't be necessary?  Also how about you update your DestinationVariableMethodArgumentResolver's
		// ConversionService when yours gets set?
		val conversionService = simpAnnotationMethodMessageHandler.conversionService as DefaultFormattingConversionService
		conversionService.addConverterFactory(Json.JacksonConverterFactory())

		log.debug("{} initialised", ConverterConfiguration::class.simpleName)
	}

	companion object {
		private val log = getLogger(ConverterConfiguration::class)
	}
}
