package jokatu.components.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Steven Weston
 */
@RestController
public class ExampleAjaxController {

	private final ExampleEventEmitter emitter;

	private final AtomicInteger requests = new AtomicInteger(0);

	@Autowired
	public ExampleAjaxController(ExampleEventEmitter emitter) {
		this.emitter = emitter;
	}

	@RequestMapping(
			value = "requestEvent",
			method = RequestMethod.POST
	)
	void requestEvent() {
		emitter.send("lol " + requests.incrementAndGet());
	}
}
