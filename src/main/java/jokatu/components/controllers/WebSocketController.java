package jokatu.components.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebSocketController {

	@RequestMapping(value = "/websocket_playground")
	String webSocketPlayground() {
		return "views/websocket_playground";
	}
}
