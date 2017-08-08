package jokatu.components.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Set up STOMP web sockets.
 */
@Configuration
@EnableWebSocket
// todo: secure web sockets
@EnableWebSocketMessageBroker
public class WebSocketConfiguration
		extends AbstractSessionWebSocketMessageBrokerConfigurer {

	private static final Logger log = LoggerFactory.getLogger(WebSocketConfiguration.class);

	@Override
	protected void configureStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws");

		log.debug("{} configured STOMP endpoints", WebSocketConfiguration.class.getSimpleName());
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableStompBrokerRelay("/topic/", "/queue/");
		registry.setPathMatcher(new AntPathMatcher("."));

		log.debug("{} configured message broker", WebSocketConfiguration.class.getSimpleName());
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
