package jokatu.components.controllers.game

import jokatu.components.markup.MarkupGenerator
import jokatu.game.GameID
import jokatu.game.exception.GameException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

import java.security.Principal

/**
 * Presents to the user all the error messages they have received for a particular game.
 */
@Controller
class ErrorLogController @Autowired constructor(private val markupGenerator: MarkupGenerator) {

	@RequestMapping("/error_log/{identity}")
	@Throws(GameException::class)
	internal fun game(@PathVariable("identity") identity: GameID, principal: Principal): ModelAndView {
		val modelAndView = ModelAndView("views/game_log")
		modelAndView.addObject("markupGenerator", markupGenerator)
		modelAndView.addObject("username", principal.name)
		modelAndView.addObject("gameId", identity)
		return modelAndView
	}
}
