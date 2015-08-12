package jokatu.components.controllers;

import jokatu.components.websocket.ExampleWebSocketHandler;
import jokatu.game.GameID;
import jokatu.game.games.empty.EmptyGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Steven Weston
 */
@RestController
public class ExampleAjaxController {

	private final ExampleWebSocketHandler emitter;

	private final SimpMessagingTemplate template;

	private final AtomicInteger requests = new AtomicInteger(0);

	@Autowired
	public ExampleAjaxController(ExampleWebSocketHandler emitter, SimpMessagingTemplate template) {
		this.emitter = emitter;
		this.template = template;
	}

	@RequestMapping(
			value = "requestEvent",
			method = RequestMethod.POST
	)
	void requestEvent() throws IOException {
		String message = "lol " + requests.incrementAndGet();

		emitter.broadcast(message);
		template.convertAndSend("/game/0", new EmptyGame(new GameID(requests.get())));
	}
}
