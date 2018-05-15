package jokatu.components.config

import jokatu.templates.IncludeOnlyOnceTemplate
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.view.groovy.GroovyMarkupConfigurer
import javax.annotation.PostConstruct

/**
 * Set up the groovy template engine to use [IncludeOnlyOnceTemplate].
 */
@Configuration
class GroovyTemplateConfig @Autowired constructor(private val groovyMarkupConfigurer: GroovyMarkupConfigurer) {

	@PostConstruct
	fun setUp() {
		groovyMarkupConfigurer.baseTemplateClass = IncludeOnlyOnceTemplate::class.java

		log.debug("{} initialised", GroovyTemplateConfig::class.java.simpleName)
	}

	companion object {

		private val log = LoggerFactory.getLogger(GroovyTemplateConfig::class.java)
	}
}
