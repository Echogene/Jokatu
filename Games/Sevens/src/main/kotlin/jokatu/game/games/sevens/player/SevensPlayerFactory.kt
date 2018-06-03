package jokatu.game.games.sevens.player

import jokatu.game.games.sevens.game.SevensGame
import jokatu.game.player.AbstractPlayerFactory
import org.springframework.stereotype.Component

@Component
class SevensPlayerFactory : AbstractPlayerFactory<SevensPlayer, SevensGame>(SevensGame::class) {
	override fun produceInCastGame(sevensGame: SevensGame, username: String): SevensPlayer {
		val player = SevensPlayer(username)
		sevensGame.register(player)
		return player
	}
}
