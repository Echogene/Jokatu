package jokatu.game.games.uzta

import jokatu.components.GameComponent
import jokatu.components.config.GameConfiguration
import jokatu.game.Game
import jokatu.game.games.uzta.game.Uzta.Companion.UZTA
import jokatu.game.games.uzta.game.UztaFactory
import jokatu.game.games.uzta.player.UztaPlayer
import jokatu.game.games.uzta.views.UztaViewResolverFactory
import jokatu.game.player.PlayerFactory
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author steven
 */
@GameComponent(gameName = UZTA)
class UztaConfiguration @Autowired
constructor(override val gameFactory: UztaFactory,
			override val viewResolverFactory: UztaViewResolverFactory) : GameConfiguration {

	override val playerFactory: PlayerFactory<*>
		get() = object : PlayerFactory<UztaPlayer> {
			override fun produce(game: Game<*>, username: String) = UztaPlayer(username)
		}
}
