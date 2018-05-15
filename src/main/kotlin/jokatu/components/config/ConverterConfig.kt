package jokatu.components.config

import jokatu.util.Json
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.format.support.DefaultFormattingConversionService
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import javax.annotation.PostConstruct

/**
 * Add a Jackson converter to the default FormatterRegistry and configure the conversion service for messages to use
 * the default Jackson converter.
 */
@Configuration
class ConverterConfig : WebMvcConfigurerAdapter() {

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

		log.debug("{} initialised", ConverterConfig::class.java.simpleName)
	}

	override fun addFormatters(registry: FormatterRegistry?) {
		registry!!.addConverterFactory(Json.JacksonConverterFactory())
	}

	companion object {
		private val log = LoggerFactory.getLogger(ConverterConfig::class.java)
	}
}
