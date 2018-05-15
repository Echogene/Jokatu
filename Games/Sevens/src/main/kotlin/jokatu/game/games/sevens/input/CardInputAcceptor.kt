package jokatu.game.games.sevens.input

import jokatu.game.cards.Card
import jokatu.game.cards.Cards
import jokatu.game.cards.Rank.SEVEN
import jokatu.game.cards.Suit
import jokatu.game.games.sevens.player.SevensPlayer
import jokatu.game.input.AnyEventInputAcceptor
import jokatu.game.input.UnacceptableInputException
import jokatu.game.result.PlayerResult
import jokatu.game.result.Result.WIN
import jokatu.game.turn.TurnManager
import ophelia.collections.set.HashSet
import java.util.*

class CardInputAcceptor(private val turnManager: TurnManager<SevensPlayer>, private val playedCards: Map<Suit, TreeSet<Card>>) : AnyEventInputAcceptor<CardInput, SevensPlayer>() {

	private val extremeCards = HashSet<Card>()

	override val inputClass: Class<CardInput>
		get() = CardInput::class.java

	override val playerClass: Class<SevensPlayer>
		get() = SevensPlayer::class.java

	@Throws(Exception::class)
	override fun acceptCastInputAndPlayer(input: CardInput, inputter: SevensPlayer) {
		turnManager.assertCurrentPlayer(inputter)

		val card = input.card
		if (!inputter.hand.contains(card)) {
			throw UnacceptableInputException("Stop cheating!  You can't play a card that's not in your hand.")
		}
		val adjacentExtremeCard = getAdjacentExtremeCard(card)
				?: throw UnacceptableInputException("There is nowhere to play that card.")
		playCard(inputter, card, adjacentExtremeCard)

		if (inputter.hand.isEmpty()) {
			fireEvent(PlayerResult(WIN, setOf(inputter)))
		} else {
			turnManager.next()
		}
	}

	private fun playCard(inputter: SevensPlayer, card: Card, adjacentExtremeCard: Card) {
		if (adjacentExtremeCard.rank !== SEVEN) {
			extremeCards.remove(adjacentExtremeCard)
		}
		extremeCards.add(card)
		playedCards[card.suit]!!.add(card)
		inputter.playCard(card)
	}

	private fun getAdjacentExtremeCard(card: Card): Card? {
		return if (card.rank === SEVEN) {
			if (extremeCards.isEmpty) {
				// You can only start the game with the seven of diamonds.
				if (card == Cards.SEVEN_OF_DIAMONDS) Cards.SEVEN_OF_DIAMONDS else null
			} else {
				card
			}
		} else extremeCards.stream()
				.filter { extreme -> isAdjacent(card, extreme) }
				.findAny()
				.orElse(null)
	}

	private fun isAdjacent(card: Card, extreme: Card): Boolean {
		return card.suit === extreme.suit && (card.rank.value == extreme.rank.value - 1 || card.rank.value == extreme.rank.value + 1)
	}
}
