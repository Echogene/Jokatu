package jokatu.game.games.sevens

import jokatu.components.GameComponent
import jokatu.components.config.GameConfiguration
import jokatu.game.games.sevens.game.SevensGame.Companion.SEVENS
import jokatu.game.games.sevens.game.SevensGameFactory
import jokatu.game.games.sevens.player.SevensPlayerFactory
import jokatu.game.games.sevens.views.SevensViewResolverFactory
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author steven
 */
@GameComponent(gameName = SEVENS)
class SevensConfiguration @Autowired
constructor(override val gameFactory: SevensGameFactory,
			override val playerFactory: SevensPlayerFactory,
			override val viewResolverFactory: SevensViewResolverFactory
) : GameConfiguration
