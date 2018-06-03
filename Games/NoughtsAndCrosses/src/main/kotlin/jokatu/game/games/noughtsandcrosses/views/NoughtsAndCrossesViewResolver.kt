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
internal class NoughtsAndCrossesViewResolver(game: NoughtsAndCrossesGame) : ViewResolver<NoughtsAndCrossesPlayer, NoughtsAndCrossesGame>(NoughtsAndCrossesPlayer::class, game) {

	override val defaultView: ModelAndView
		get() = ModelAndView(
				when (game.currentStage) {
					is JoiningStage<*> -> "views/game_join"
					is AllegianceStage -> "views/allegiance"
					else -> "views/noughts_and_crosses"
				}
		)

	override fun getViewFor(player: NoughtsAndCrossesPlayer): ModelAndView {
		return defaultView
	}
}
