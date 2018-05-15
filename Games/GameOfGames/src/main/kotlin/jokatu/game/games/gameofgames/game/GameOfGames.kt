package jokatu.game.games.gameofgames.game

import jokatu.game.Game
import jokatu.game.GameID
import jokatu.game.player.StandardPlayer
import jokatu.game.stage.machine.SingleStageMachine

/**
 * The game of games.  The beginning.  The end.
 *
 * A game you play to create games.
 */
class GameOfGames internal constructor(gameID: GameID) : Game<StandardPlayer>(gameID) {

	override val gameName: String
		get() = GAME_OF_GAMES

	init {
		stageMachine = SingleStageMachine(GameOfGameStage())
	}

	companion object {
		const val GAME_OF_GAMES = "Game of games"
	}
}
