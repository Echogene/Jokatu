package jokatu.game.games.uzta.input

import jokatu.game.input.DeserialisationException
import jokatu.game.input.SingleKeyInputDeserialiser
import org.springframework.stereotype.Component

@Component
class RandomiseGraphInputDeserialiser : SingleKeyInputDeserialiser<RandomiseGraphInput>("randomise") {
	@Throws(DeserialisationException::class)
	override fun deserialiseSingleValue(json: Map<String, Any>, value: Any): RandomiseGraphInput {
		return RandomiseGraphInput()
	}
}
