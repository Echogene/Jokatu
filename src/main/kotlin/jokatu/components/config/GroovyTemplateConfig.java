package jokatu.components.config;

import jokatu.templates.IncludeOnlyOnceTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.groovy.GroovyMarkupConfigurer;

import javax.annotation.PostConstruct;

/**
 * Set up the groovy template engine to use {@link IncludeOnlyOnceTemplate}.
 */
@Configuration
public class GroovyTemplateConfig {

	private static final Logger log = LoggerFactory.getLogger(GroovyTemplateConfig.class);

	private final GroovyMarkupConfigurer groovyMarkupConfigurer;

	@Autowired
	public GroovyTemplateConfig(GroovyMarkupConfigurer groovyMarkupConfigurer) {
		this.groovyMarkupConfigurer = groovyMarkupConfigurer;
	}

	@PostConstruct
	public void setUp() {
		groovyMarkupConfigurer.setBaseTemplateClass(IncludeOnlyOnceTemplate.class);

		log.debug("{} initialised", GroovyTemplateConfig.class.getSimpleName());
	}
}
