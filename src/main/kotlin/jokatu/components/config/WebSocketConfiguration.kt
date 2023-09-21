package jokatu.components.config

import org.slf4j.getLogger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.session.MapSession
import org.springframework.session.MapSessionRepository
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer
import org.springframework.util.AntPathMatcher
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import java.util.concurrent.ConcurrentHashMap

/**
 * Set up STOMP web sockets.
 */
@Configuration
@EnableScheduling
// todo: secure web sockets
@EnableWebSocketMessageBroker
class WebSocketConfiguration : AbstractSessionWebSocketMessageBrokerConfigurer<MapSession>() {

	override fun configureStompEndpoints(registry: StompEndpointRegistry) {
		registry.addEndpoint("/ws")

		log.debug("{} configured STOMP endpoints", WebSocketConfiguration::class.simpleName)
	}

	override fun configureMessageBroker(registry: MessageBrokerRegistry) {
		registry.enableStompBrokerRelay("/topic/", "/queue/")
		registry.setPathMatcher(AntPathMatcher("."))

		log.debug("{} configured message broker", WebSocketConfiguration::class.simpleName)
	}

	companion object {
		private val log = getLogger(WebSocketConfiguration::class)
	}

	//	@Override
	//	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
	//		messages.anyMessage().authenticated();
	//	}
}
