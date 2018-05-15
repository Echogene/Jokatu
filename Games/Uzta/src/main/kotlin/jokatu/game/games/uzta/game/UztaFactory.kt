package jokatu.game.games.uzta.game

import jokatu.game.AbstractGameFactory
import jokatu.game.GameID
import org.springframework.stereotype.Component

@Component
class UztaFactory : AbstractGameFactory<Uzta>() {
	override fun produce(gameID: GameID, creatorName: String): Uzta {
		return Uzta(gameID)
	}
}
