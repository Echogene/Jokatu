package jokatu.game.games.gameofgames.views

import jokatu.game.games.gameofgames.game.GameOfGames
import jokatu.game.player.StandardPlayer
import jokatu.game.viewresolver.ViewResolver
import org.springframework.web.servlet.ModelAndView

internal class GameOfGamesViewResolver(game: GameOfGames) : ViewResolver<StandardPlayer, GameOfGames>(StandardPlayer::class, game) {

	override val defaultView: ModelAndView
		get() = ModelAndView("views/game_admin")

	override fun getViewFor(player: StandardPlayer): ModelAndView {
		return defaultView
	}
}
