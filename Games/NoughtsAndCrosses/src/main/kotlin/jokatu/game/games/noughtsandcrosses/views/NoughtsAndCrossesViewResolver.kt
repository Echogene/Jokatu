package jokatu.game.games.noughtsandcrosses.views

import jokatu.game.games.noughtsandcrosses.game.AllegianceStage
import jokatu.game.games.noughtsandcrosses.game.NoughtsAndCrossesGame
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer
import jokatu.game.stage.JoiningStage
import jokatu.game.viewresolver.ViewResolver
import org.springframework.web.servlet.ModelAndView

/**
 * @author steven
 */
internal class NoughtsAndCrossesViewResolver(game: NoughtsAndCrossesGame) : ViewResolver<NoughtsAndCrossesPlayer, NoughtsAndCrossesGame>(game) {

	override val defaultView: ModelAndView
		get() {
			val view: String
			if (game.currentStage is JoiningStage<*>) {
				view = "views/game_join"

			} else if (game.currentStage is AllegianceStage) {
				view = "views/allegiance"

			} else {
				view = "views/noughts_and_crosses"
			}
			return ModelAndView(view)
		}

	override val playerClass: Class<NoughtsAndCrossesPlayer>
		get() = NoughtsAndCrossesPlayer::class.java

	override fun getViewFor(player: NoughtsAndCrossesPlayer): ModelAndView {
		return defaultView
	}
}
