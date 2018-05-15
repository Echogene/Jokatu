package jokatu.game.games.echo.game

import jokatu.game.AbstractGameFactory
import jokatu.game.GameID
import org.springframework.stereotype.Component

@Component
class EchoFactory : AbstractGameFactory<EchoGame>() {

	override fun produce(gameID: GameID, creatorName: String): EchoGame {
		return EchoGame(gameID)
	}
}
