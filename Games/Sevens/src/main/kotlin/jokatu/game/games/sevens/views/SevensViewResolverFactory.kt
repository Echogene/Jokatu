package jokatu.game.games.sevens.views

import jokatu.game.games.sevens.game.SevensGame
import jokatu.game.games.sevens.player.SevensPlayer
import jokatu.game.viewresolver.ViewResolver
import jokatu.game.viewresolver.ViewResolverFactory
import org.springframework.stereotype.Component

/**
 * @author steven
 */
@Component
class SevensViewResolverFactory : ViewResolverFactory<SevensPlayer, SevensGame>() {
	override val gameClass: Class<SevensGame>
		get() = SevensGame::class.java

	override fun getResolverFor(castGame: SevensGame): ViewResolver<SevensPlayer, SevensGame> {
		return SevensViewResolver(castGame)
	}
}
