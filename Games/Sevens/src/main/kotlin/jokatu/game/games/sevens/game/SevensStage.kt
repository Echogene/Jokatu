package jokatu.game.games.sevens.game

import jokatu.game.MultiInputStage
import jokatu.game.cards.Card
import jokatu.game.cards.Cards
import jokatu.game.cards.Suit
import jokatu.game.games.sevens.input.CardInputAcceptor
import jokatu.game.games.sevens.input.SkipInputAcceptor
import jokatu.game.games.sevens.player.SevensPlayer
import jokatu.game.status.StandardTextStatus
import jokatu.game.turn.TurnManager
import java.util.*
import java.util.Collections.shuffle

internal class SevensStage(players: Map<String, SevensPlayer>, status: StandardTextStatus, playedCards: Map<Suit, TreeSet<Card>>) : MultiInputStage() {

	private val players: List<SevensPlayer>
	private val turnManager: TurnManager<SevensPlayer>

	private val startingPlayer: SevensPlayer
		get() = players.stream()
				.filter { player -> player.hand.contains(Cards.SEVEN_OF_DIAMONDS) }
				.findAny()
				.orElseThrow { IllegalStateException() }

	init {
		this.players = assignDealOrder(players)

		dealHands()
		sortHands()

		turnManager = TurnManager(this.players)
		turnManager.observe { e ->
			status.text = "Waiting for ${e.newPlayer} to play a card or pass."
			// Forward the event.
			fireEvent(e)
		}

		addInputAcceptor(CardInputAcceptor(turnManager, playedCards))
		addInputAcceptor(SkipInputAcceptor(turnManager))
	}

	override fun start() {
		val startingPlayer = startingPlayer
		turnManager.currentPlayer = startingPlayer
	}

	private fun assignDealOrder(players: Map<String, SevensPlayer>): List<SevensPlayer> {
		return ArrayList(players.values)
	}

	private fun dealHands() {
		val deck = Cards.newDeck
		shuffle(deck)
		for (i in deck.indices) {
			val card = deck[i]
			val player = players[i % players.size]
			player.drawCard(card)
		}
	}

	private fun sortHands() {
		players.stream()
				.map { it.hand }
				.forEach { hand ->
					hand.sortWith(Comparator { card1, card2 ->
						val suitComparison = card1.suit.compareTo(card2.suit)
						return@Comparator if (suitComparison == 0) {
							card1.rank.compareTo(card2.rank)
						} else {
							suitComparison
						}
					})
				}
	}
}
