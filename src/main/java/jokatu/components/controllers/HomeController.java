package jokatu.components.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Steven Weston
 */
@Controller
public class HomeController {

	@RequestMapping("/")
	String home() {
		return "views/home";
	}
}
