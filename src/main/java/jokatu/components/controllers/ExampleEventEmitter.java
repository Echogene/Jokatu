package jokatu.components.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Steven Weston
 */
@RestController
@RequestMapping("/events/**")
public class ExampleEventEmitter {

	public static final String TEXT_EVENT_STREAM = "text/event-stream";

	private final Set<SseEmitter> emitters = new HashSet<>();

	@RequestMapping(
			value = "exampleStream",
			produces = TEXT_EVENT_STREAM
	)
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
