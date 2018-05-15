package jokatu.game.games.echo

import jokatu.components.GameComponent
import jokatu.components.config.GameConfiguration
import jokatu.game.Game
import jokatu.game.games.echo.game.EchoFactory
import jokatu.game.games.echo.game.EchoGame.Companion.ECHO
import jokatu.game.games.echo.views.EchoViewResolverFactory
import jokatu.game.player.PlayerFactory
import jokatu.game.player.StandardPlayer
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author steven
 */
@GameComponent(gameName = ECHO)
class EchoConfiguration @Autowired
constructor(override val gameFactory: EchoFactory,
			override val viewResolverFactory: EchoViewResolverFactory
) : GameConfiguration {

	override val playerFactory: PlayerFactory<*>
		get() = object : PlayerFactory<StandardPlayer> {
			override fun produce(game: Game<*>, username: String) = StandardPlayer(username)
		}
}
