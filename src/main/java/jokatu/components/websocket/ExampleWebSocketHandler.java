package jokatu.components.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.newSetFromMap;

@Component
public class ExampleWebSocketHandler extends TextWebSocketHandler {

	private final Set<WebSocketSession> sessions = newSetFromMap(new ConcurrentHashMap<>());

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		broadcast(message.getPayload());
	}

	public void broadcast(String message) throws IOException {
		TextMessage messageToSend = new TextMessage(message);
		for (WebSocketSession otherSession : sessions) {
			otherSession.sendMessage(messageToSend);
		}
	}
}
