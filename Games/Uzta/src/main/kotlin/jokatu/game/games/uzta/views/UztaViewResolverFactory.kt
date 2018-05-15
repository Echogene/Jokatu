package jokatu.game.games.uzta.views

import jokatu.game.games.uzta.game.Uzta
import jokatu.game.games.uzta.player.UztaPlayer
import jokatu.game.viewresolver.ViewResolver
import jokatu.game.viewresolver.ViewResolverFactory
import org.springframework.stereotype.Component

/**
 * @author steven
 */
@Component
class UztaViewResolverFactory : ViewResolverFactory<UztaPlayer, Uzta>() {
	override val gameClass: Class<Uzta>
		get() = Uzta::class.java

	override fun getResolverFor(castGame: Uzta): ViewResolver<UztaPlayer, Uzta> {
		return UztaViewResolver(castGame)
	}
}
