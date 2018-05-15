package jokatu.components.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * @author Steven Weston
 */
@Controller
class HomeController {

	/**
	 * Go to the login page.
	 */
	@RequestMapping("/login")
	internal fun login(): String {
		return "views/log_in"
	}

	/**
	 * Go to the home page.
	 */
	@RequestMapping("/")
	internal fun loggedIn(): String {
		return "redirect:/game/0"
	}
}
