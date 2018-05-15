package jokatu.game.games.rockpaperscissors.views

import jokatu.game.games.rockpaperscissors.game.RockPaperScissorsGame
import jokatu.game.player.StandardPlayer
import jokatu.game.stage.JoiningStage
import jokatu.game.viewresolver.ViewResolver
import org.springframework.web.servlet.ModelAndView

/**
 * @author steven
 */
internal class RockPaperScissorsViewResolver(game: RockPaperScissorsGame) : ViewResolver<StandardPlayer, RockPaperScissorsGame>(game) {

	override val defaultView: ModelAndView
		get() {
			val view = when {
				game.currentStage is JoiningStage<*> -> "views/game_join"
				else -> "views/rock_paper_scissors"
			}
			return ModelAndView(view)
		}

	override val playerClass: Class<StandardPlayer>
		get() = StandardPlayer::class.java

	override fun getViewFor(player: StandardPlayer): ModelAndView {
		return defaultView
	}
}
