package jokatu.game.games.uzta.views

import jokatu.game.games.uzta.game.FirstPlacementStage
import jokatu.game.games.uzta.game.SetupStage
import jokatu.game.games.uzta.game.Uzta
import jokatu.game.games.uzta.player.UztaPlayer
import jokatu.game.stage.MinAndMaxJoiningStage
import jokatu.game.viewresolver.ViewResolver
import org.springframework.web.servlet.ModelAndView

internal class UztaViewResolver(castGame: Uzta) : ViewResolver<UztaPlayer, Uzta>(castGame) {

	override val defaultView: ModelAndView
		get() {
			val currentStage = game.currentStage
			val view = when (currentStage) {
				is MinAndMaxJoiningStage<*> -> "views/game_join_with_start"
				is SetupStage -> "views/uzta_setup"
				is FirstPlacementStage -> "views/uzta"
				else -> "views/uzta_main"
			}
			return ModelAndView(view)
		}

	override val playerClass: Class<UztaPlayer>
		get() = UztaPlayer::class.java

	override fun getViewFor(player: UztaPlayer): ModelAndView {
		return defaultView
	}
}
