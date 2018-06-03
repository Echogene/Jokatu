package jokatu.game.games.gameofgames.views

import jokatu.game.games.gameofgames.game.GameOfGames
import jokatu.game.player.StandardPlayer
import jokatu.game.viewresolver.ViewResolver
import jokatu.game.viewresolver.ViewResolverFactory
import org.springframework.stereotype.Component

@Component
class GameOfGamesViewResolverFactory : ViewResolverFactory<StandardPlayer, GameOfGames>(GameOfGames::class) {
	override fun getResolverFor(castGame: GameOfGames): ViewResolver<StandardPlayer, GameOfGames> {
		return GameOfGamesViewResolver(castGame)
	}
}
