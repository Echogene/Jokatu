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
class NoughtsAndCrossesViewResolverFactory : ViewResolverFactory<NoughtsAndCrossesPlayer, NoughtsAndCrossesGame>(NoughtsAndCrossesGame::class) {
	override fun getResolverFor(castGame: NoughtsAndCrossesGame): ViewResolver<NoughtsAndCrossesPlayer, NoughtsAndCrossesGame> {
		return NoughtsAndCrossesViewResolver(castGame)
	}
}
