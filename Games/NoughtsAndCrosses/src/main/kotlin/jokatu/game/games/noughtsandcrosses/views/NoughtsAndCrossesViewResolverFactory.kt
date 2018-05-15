package jokatu.game.games.noughtsandcrosses.views

import jokatu.game.games.noughtsandcrosses.game.NoughtsAndCrossesGame
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer
import jokatu.game.viewresolver.ViewResolver
import jokatu.game.viewresolver.ViewResolverFactory
import org.springframework.stereotype.Component

/**
 * @author steven
 */
@Component
class NoughtsAndCrossesViewResolverFactory : ViewResolverFactory<NoughtsAndCrossesPlayer, NoughtsAndCrossesGame>() {
	override val gameClass: Class<NoughtsAndCrossesGame>
		get() = NoughtsAndCrossesGame::class.java

	override fun getResolverFor(castGame: NoughtsAndCrossesGame): ViewResolver<NoughtsAndCrossesPlayer, NoughtsAndCrossesGame> {
		return NoughtsAndCrossesViewResolver(castGame)
	}
}
