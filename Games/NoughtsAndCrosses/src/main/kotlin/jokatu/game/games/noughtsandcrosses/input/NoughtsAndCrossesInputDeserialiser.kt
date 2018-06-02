package jokatu.game.games.noughtsandcrosses.input

import jokatu.game.input.DeserialisationException
import jokatu.game.input.TypedSingleKeyInputDeserialiser
import org.springframework.stereotype.Component

@Component
class NoughtsAndCrossesInputDeserialiser : TypedSingleKeyInputDeserialiser<Int, NoughtsAndCrossesInput>(Int::class) {

	override val keyName: String
		get() = "choice"

	@Throws(DeserialisationException::class)
	override fun deserialiseTypedSingleValue(json: Map<String, Any>, choice: Int): NoughtsAndCrossesInput {
		return NoughtsAndCrossesInput(choice)
	}
}
