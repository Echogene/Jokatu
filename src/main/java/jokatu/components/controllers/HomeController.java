package jokatu.components.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Steven Weston
 */
@Controller
public class HomeController {

	@RequestMapping("/login")
	String login() {
		return "views/log_in";
	}

	@RequestMapping("/")
	String loggedIn() {
		return "views/home";
	}
}
