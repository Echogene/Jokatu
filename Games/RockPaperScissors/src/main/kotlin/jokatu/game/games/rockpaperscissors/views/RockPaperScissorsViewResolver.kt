package jokatu.game.games.rockpaperscissors.views

import jokatu.game.games.rockpaperscissors.game.RockPaperScissorsGame
import jokatu.game.player.StandardPlayer
import jokatu.game.stage.JoiningStage
import jokatu.game.viewresolver.ViewResolver
import org.springframework.web.servlet.ModelAndView

/**
 * @author steven
 */
internal class RockPaperScissorsViewResolver(game: RockPaperScissorsGame) : ViewResolver<StandardPlayer, RockPaperScissorsGame>(StandardPlayer::class, game) {

	override val defaultView: ModelAndView
		get() = ModelAndView(
				when (game.currentStage) {
					is JoiningStage<*> -> "views/game_join"
					else -> "views/rock_paper_scissors"
				}
		)

	override fun getViewFor(player: StandardPlayer): ModelAndView {
		return defaultView
	}
}
