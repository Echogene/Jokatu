package jokatu.components.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Steven Weston
 */
@Controller
public class EventController {

	public static final String TEXT_EVENT_STREAM = "text/event-stream";

	@RequestMapping(value = "/event_playground")
	String eventPlayground() {
		return "views/event_playground";
	}

	private final Set<SseEmitter> emitters = new HashSet<>();

	@RequestMapping(
			value = "/events/exampleStream",
			produces = TEXT_EVENT_STREAM
	)
	@ResponseBody
	SseEmitter exampleEventStream() throws InterruptedException {

		SseEmitter emitter = new SseEmitter();
		emitters.add(emitter);
		emitter.onCompletion(() -> emitters.remove(emitter));
		return emitter;
	}

	synchronized void send(String message) {

		Iterator<SseEmitter> iterator = emitters.iterator();
		while (iterator.hasNext()) {
			SseEmitter emitter = iterator.next();
			try {
				emitter.send(message);
			} catch (IOException e) {
				iterator.remove();
			}
		}
	}
}
