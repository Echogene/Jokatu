package jokatu.game.games.rockpaperscissors.views

import jokatu.game.games.rockpaperscissors.game.RockPaperScissorsGame
import jokatu.game.player.StandardPlayer
import jokatu.game.viewresolver.ViewResolver
import jokatu.game.viewresolver.ViewResolverFactory
import org.springframework.stereotype.Component

/**
 * @author steven
 */
@Component
class RockPaperScissorsViewResolverFactory : ViewResolverFactory<StandardPlayer, RockPaperScissorsGame>() {
	override val gameClass: Class<RockPaperScissorsGame>
		get() = RockPaperScissorsGame::class.java

	override fun getResolverFor(castGame: RockPaperScissorsGame): ViewResolver<StandardPlayer, RockPaperScissorsGame> {
		return RockPaperScissorsViewResolver(castGame)
	}
}
