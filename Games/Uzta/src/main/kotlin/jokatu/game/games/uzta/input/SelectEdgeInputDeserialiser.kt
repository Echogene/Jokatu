package jokatu.game.games.uzta.input

import jokatu.game.input.DeserialisationException
import jokatu.game.input.InputDeserialiser
import org.springframework.stereotype.Component

@Component
class SelectEdgeInputDeserialiser : InputDeserialiser<SelectEdgeInput>() {
	@Throws(DeserialisationException::class)
	override fun deserialise(json: Map<String, Any>): SelectEdgeInput {
		val start = getMandatoryKeyValue("start", json)
		val startId = castValue(String::class, "start", start, json)

		val end = getMandatoryKeyValue("end", json)
		val endId = castValue(String::class, "end", end, json)

		return SelectEdgeInput(startId, endId)
	}
}
