package jokatu.components.config;

import jokatu.components.websocket.ExampleWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Component
@EnableWebSocket
// todo: secure web sockets
//@EnableWebSocketMessageBroker
public class WebSocketConfiguration
//		extends AbstractSecurityWebSocketMessageBrokerConfigurer
		implements WebSocketConfigurer {

	private final ExampleWebSocketHandler exampleHandler;

	@Autowired
	public WebSocketConfiguration(ExampleWebSocketHandler exampleHandler) {
		this.exampleHandler = exampleHandler;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(exampleHandler, "/test");
	}

//	@Override
//	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
//		messages.anyMessage().authenticated();
//	}
}
