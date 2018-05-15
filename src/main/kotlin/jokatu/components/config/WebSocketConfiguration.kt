package jokatu.components.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.session.MapSession
import org.springframework.session.MapSessionRepository
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer
import org.springframework.util.AntPathMatcher
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry

/**
 * Set up STOMP web sockets.
 */
@Configuration
@EnableWebSocket
// todo: secure web sockets
@EnableWebSocketMessageBroker
class WebSocketConfiguration : AbstractSessionWebSocketMessageBrokerConfigurer<MapSession>() {

	override fun configureStompEndpoints(registry: StompEndpointRegistry) {
		registry.addEndpoint("/ws")

		log.debug("{} configured STOMP endpoints", WebSocketConfiguration::class.java.simpleName)
	}

	override fun configureMessageBroker(registry: MessageBrokerRegistry?) {
		registry!!.enableStompBrokerRelay("/topic/", "/queue/")
		registry.setPathMatcher(AntPathMatcher("."))

		log.debug("{} configured message broker", WebSocketConfiguration::class.java.simpleName)
	}

	@Bean
	fun sessionRepository(): MapSessionRepository {
		return MapSessionRepository()
	}

	companion object {
		private val log = LoggerFactory.getLogger(WebSocketConfiguration::class.java)
	}

	//	@Override
	//	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
	//		messages.anyMessage().authenticated();
	//	}
}
