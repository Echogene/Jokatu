package jokatu.game.games.gameofgames.game

import jokatu.game.AbstractGameFactory
import jokatu.game.GameID
import org.springframework.stereotype.Component

@Component
class GameOfGamesFactory : AbstractGameFactory<GameOfGames>() {
	override fun produce(gameID: GameID, creatorName: String): GameOfGames {
		return GameOfGames(gameID)
	}
}
