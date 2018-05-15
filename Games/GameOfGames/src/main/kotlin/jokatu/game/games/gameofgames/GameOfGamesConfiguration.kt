package jokatu.game.games.gameofgames

import jokatu.components.GameComponent
import jokatu.components.config.GameConfiguration
import jokatu.game.Game
import jokatu.game.games.gameofgames.game.GameOfGames
import jokatu.game.games.gameofgames.game.GameOfGamesFactory
import jokatu.game.games.gameofgames.views.GameOfGamesViewResolverFactory
import jokatu.game.player.PlayerFactory
import jokatu.game.player.StandardPlayer
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author steven
 */
@GameComponent(gameName = GameOfGames.GAME_OF_GAMES)
class GameOfGamesConfiguration @Autowired
constructor(
		override val gameFactory: GameOfGamesFactory,
		override val viewResolverFactory: GameOfGamesViewResolverFactory
) : GameConfiguration {

	override val playerFactory: PlayerFactory<*>
		get() = object : PlayerFactory<StandardPlayer> {
			override fun produce(game: Game<*>, username: String) = StandardPlayer(username)
		}
}
