package jokatu.components.config;

import jokatu.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import javax.annotation.PostConstruct;

/**
 * Set up STOMP web sockets and configure the conversion service for messages to use the default Jackson converter.
 */
@Configuration
@EnableWebSocket
// todo: secure web sockets
@EnableWebSocketMessageBroker
public class WebSocketConfiguration
		extends AbstractSessionWebSocketMessageBrokerConfigurer {

	@Autowired
	private SimpAnnotationMethodMessageHandler simpAnnotationMethodMessageHandler;

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
	protected void configureStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws");
	}

	@Bean
	public MapSessionRepository sessionRepository() {
		return new MapSessionRepository();
	}

//	@Override
//	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
//		messages.anyMessage().authenticated();
//	}
}
