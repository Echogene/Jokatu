package jokatu.game.games.sevens.game

import jokatu.game.AbstractGameFactory
import jokatu.game.GameID
import org.springframework.stereotype.Component

@Component
class SevensGameFactory : AbstractGameFactory<SevensGame>() {

	override fun produce(gameID: GameID, creatorName: String): SevensGame {
		return SevensGame(gameID)
	}
}
