package jokatu.game.games.sevens.game

import jokatu.game.Game
import jokatu.game.GameID
import jokatu.game.cards.Card
import jokatu.game.cards.Suit
import jokatu.game.games.sevens.player.SevensPlayer
import jokatu.game.stage.GameOverStage
import jokatu.game.stage.MinAndMaxJoiningStage
import jokatu.game.stage.machine.SequentialStageMachine
import jokatu.game.status.StandardTextStatus
import java.util.*

class SevensGame internal constructor(identifier: GameID) : Game<SevensPlayer>(identifier) {

	private val status = StandardTextStatus()
	private val playedCards = object : HashMap<Suit, TreeSet<Card>>() {
		init {
			Arrays.stream(Suit.values())
					.forEach { suit -> put(suit, TreeSet { c1, c2 -> c1.rank.compareTo(c2.rank) }) }
		}
	}

	override val gameName: String
		get() = SEVENS

	init {
		stageMachine = SequentialStageMachine(
				{ MinAndMaxJoiningStage(SevensPlayer::class, players, 3, 7, status) },
				{ SevensStage(players, status, playedCards) },
				{ GameOverStage(status) }
		)

		status.observe(::fireEvent)
	}

	fun register(player: SevensPlayer) {
		player.observe(::fireEvent)
	}

	fun getCardsOfSuitPlayed(suit: Suit) = playedCards[suit]!!

	companion object {
		const val SEVENS = "Sevens"
	}
}
