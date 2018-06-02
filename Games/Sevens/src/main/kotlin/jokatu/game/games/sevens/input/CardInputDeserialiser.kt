package jokatu.game.games.sevens.input

import jokatu.game.cards.Cards
import jokatu.game.input.DeserialisationException
import jokatu.game.input.TypedSingleKeyInputDeserialiser
import org.springframework.stereotype.Component

@Component
class CardInputDeserialiser : TypedSingleKeyInputDeserialiser<String, CardInput>(String::class) {

	override val keyName: String
		get() = "card"

	@Throws(DeserialisationException::class)
	override fun deserialiseTypedSingleValue(json: Map<String, Any>, cardDisplay: String): CardInput {
		if (!Cards.CARDS_BY_DISPLAY.containsKey(cardDisplay)) {
			throw DeserialisationException(json, "The value for 'card' was not a valid card.")
		}
		return CardInput(Cards.CARDS_BY_DISPLAY[cardDisplay]!!)
	}
}
