package jokatu.test;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class TestConfig {

	/**
	 * Mock the {@link SimpMessagingTemplate}.
	 */
	@Bean
	public SimpMessagingTemplate simpMessagingTemplate() {
		return Mockito.mock(SimpMessagingTemplate.class);
	}
}
