package jokatu.components.config

import jokatu.util.Json
import org.slf4j.getLogger
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Add a Jackson converter to the default FormatterRegistry.
 */
@Configuration
class ConverterConfiguration : WebMvcConfigurer {

	override fun addFormatters(registry: FormatterRegistry) {
		registry.addConverterFactory(Json.JacksonConverterFactory())
	}
}
