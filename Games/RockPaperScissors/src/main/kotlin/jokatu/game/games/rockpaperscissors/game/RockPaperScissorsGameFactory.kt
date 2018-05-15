package jokatu.game.games.rockpaperscissors.game

import jokatu.game.AbstractGameFactory
import jokatu.game.GameID
import org.springframework.stereotype.Component

@Component
class RockPaperScissorsGameFactory : AbstractGameFactory<RockPaperScissorsGame>() {

	override fun produce(gameID: GameID, creatorName: String): RockPaperScissorsGame {
		return RockPaperScissorsGame(gameID)
	}
}
