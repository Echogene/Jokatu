package jokatu.components.config;

import jokatu.util.Json;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Adds a Jackson converter to the default FormatterRegistry.
 */
@Configuration
public class ConverterConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(new Json.JacksonConverterFactory());
	}
}
