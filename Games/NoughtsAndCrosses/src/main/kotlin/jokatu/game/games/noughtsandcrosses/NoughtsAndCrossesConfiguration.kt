package jokatu.game.games.noughtsandcrosses

import jokatu.components.GameComponent
import jokatu.components.config.GameConfiguration
import jokatu.game.Game
import jokatu.game.games.noughtsandcrosses.game.NoughtsAndCrossesGame.Companion.NOUGHTS_AND_CROSSES
import jokatu.game.games.noughtsandcrosses.game.NoughtsAndCrossesGameFactory
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer
import jokatu.game.games.noughtsandcrosses.views.NoughtsAndCrossesViewResolverFactory
import jokatu.game.player.PlayerFactory
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author steven
 */
@GameComponent(gameName = NOUGHTS_AND_CROSSES)
class NoughtsAndCrossesConfiguration @Autowired
constructor(
		override val gameFactory: NoughtsAndCrossesGameFactory,
		override val viewResolverFactory: NoughtsAndCrossesViewResolverFactory
) : GameConfiguration {

	override val playerFactory: PlayerFactory<*>
		get() = object : PlayerFactory<NoughtsAndCrossesPlayer> {
			override fun produce(game: Game<*>, username: String) = NoughtsAndCrossesPlayer(username)
		}
}
