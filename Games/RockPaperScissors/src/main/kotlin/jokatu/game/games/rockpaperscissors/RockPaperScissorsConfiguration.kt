package jokatu.game.games.rockpaperscissors

import jokatu.components.GameComponent
import jokatu.components.config.GameConfiguration
import jokatu.game.Game
import jokatu.game.games.rockpaperscissors.game.RockPaperScissorsGame.Companion.ROCK_PAPER_SCISSORS
import jokatu.game.games.rockpaperscissors.game.RockPaperScissorsGameFactory
import jokatu.game.games.rockpaperscissors.views.RockPaperScissorsViewResolverFactory
import jokatu.game.player.PlayerFactory
import jokatu.game.player.StandardPlayer
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author steven
 */
@GameComponent(gameName = ROCK_PAPER_SCISSORS)
class RockPaperScissorsConfiguration @Autowired
constructor(
		override val gameFactory: RockPaperScissorsGameFactory,
		override val viewResolverFactory: RockPaperScissorsViewResolverFactory
) : GameConfiguration {

	override val playerFactory: PlayerFactory<*>
		get() = object : PlayerFactory<StandardPlayer> {
			override fun produce(game: Game<*>, username: String) = StandardPlayer(username)
		}
}
