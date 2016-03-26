package jokatu.components.config;

import jokatu.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;

/**
 * Add a Jackson converter to the default FormatterRegistry and configure the conversion service for messages to use
 * the default Jackson converter.
 */
@Configuration
public class ConverterConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private SimpAnnotationMethodMessageHandler simpAnnotationMethodMessageHandler;

	@SuppressWarnings("unused") // This is called by Spring
	@PostConstruct
	public void init() {
		// How about you use the ConversionService that already exists, rather than creating your own, and then this
		// wouldn't be necessary?  Also how about you update your DestinationVariableMethodArgumentResolver's
		// ConversionService when yours gets set?
		DefaultFormattingConversionService conversionService
				= (DefaultFormattingConversionService) simpAnnotationMethodMessageHandler.getConversionService();
		conversionService.addConverterFactory(new Json.JacksonConverterFactory());
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(new Json.JacksonConverterFactory());
	}
}
