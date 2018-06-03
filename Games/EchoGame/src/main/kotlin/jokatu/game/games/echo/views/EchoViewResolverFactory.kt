package jokatu.game.games.echo.views

import jokatu.game.games.echo.game.EchoGame
import jokatu.game.player.StandardPlayer
import jokatu.game.viewresolver.ViewResolver
import jokatu.game.viewresolver.ViewResolverFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.ModelAndView

/**
 * @author steven
 */
@Component
class EchoViewResolverFactory : ViewResolverFactory<StandardPlayer, EchoGame>() {
	override val gameClass: Class<EchoGame>
		get() = EchoGame::class.java

	override fun getResolverFor(castGame: EchoGame): ViewResolver<StandardPlayer, EchoGame> {
		return object : ViewResolver<StandardPlayer, EchoGame>(StandardPlayer::class, castGame) {
			override val defaultView: ModelAndView
				get() = ModelAndView("views/echo_view")

			override fun getViewFor(player: StandardPlayer): ModelAndView {
				return defaultView
			}
		}
	}
}
