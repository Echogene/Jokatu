package jokatu.components.controllers;

import jokatu.components.websocket.ExampleWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
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

	private final AtomicInteger requests = new AtomicInteger(0);

	@Autowired
	public ExampleAjaxController(ExampleWebSocketHandler emitter) {
		this.emitter = emitter;
	}

	@RequestMapping(
			value = "requestEvent",
			method = RequestMethod.POST
	)
	void requestEvent() throws IOException {
		emitter.broadcast("lol " + requests.incrementAndGet());
	}
}
