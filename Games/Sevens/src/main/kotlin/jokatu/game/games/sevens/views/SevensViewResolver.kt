package jokatu.game.games.sevens.views

import jokatu.game.games.sevens.game.SevensGame
import jokatu.game.games.sevens.player.SevensPlayer
import jokatu.game.stage.MinAndMaxJoiningStage
import jokatu.game.viewresolver.ViewResolver
import org.springframework.web.servlet.ModelAndView

internal class SevensViewResolver(game: SevensGame) : ViewResolver<SevensPlayer, SevensGame>(SevensPlayer::class, game) {

	override val defaultView: ModelAndView
		get() = ModelAndView(
				when (game.currentStage) {
					is MinAndMaxJoiningStage<*> -> "views/game_join_with_start"
					else -> "views/sevens"
				}
		)

	override fun getViewFor(player: SevensPlayer): ModelAndView {
		return defaultView
	}
}
