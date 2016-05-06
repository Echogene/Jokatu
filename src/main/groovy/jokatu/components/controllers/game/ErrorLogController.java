package jokatu.components.controllers.game;

import jokatu.components.markup.MarkupGenerator;
import jokatu.game.GameID;
import jokatu.game.exception.GameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class ErrorLogController {

	private final MarkupGenerator markupGenerator;

	@Autowired
	public ErrorLogController(MarkupGenerator markupGenerator) {
		this.markupGenerator = markupGenerator;
	}

	@RequestMapping("/error_log/{identity}")
	ModelAndView game(@PathVariable("identity") GameID identity, Principal principal) throws GameException {
		ModelAndView modelAndView = new ModelAndView("views/game_log");
		modelAndView.addObject("markupGenerator", markupGenerator);
		modelAndView.addObject("username", principal.getName());
		modelAndView.addObject("gameId", identity);
		return modelAndView;
	}
}
