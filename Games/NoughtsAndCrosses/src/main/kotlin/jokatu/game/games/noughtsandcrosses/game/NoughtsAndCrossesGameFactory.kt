package jokatu.game.games.noughtsandcrosses.game

import jokatu.game.AbstractGameFactory
import jokatu.game.GameID
import org.springframework.stereotype.Component

@Component
class NoughtsAndCrossesGameFactory : AbstractGameFactory<NoughtsAndCrossesGame>() {

	override fun produce(gameID: GameID, creatorName: String): NoughtsAndCrossesGame {
		return NoughtsAndCrossesGame(gameID)
	}
}
