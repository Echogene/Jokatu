package jokatu.components.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Steven Weston
 */
@Controller
public class HomeController {

	/**
	 * Go to the login page.
	 */
	@RequestMapping("/login")
	String login() {
		return "views/log_in";
	}

	/**
	 * Go to the home page.
	 */
	@RequestMapping("/")
	String loggedIn() {
		return "views/home";
	}
}
