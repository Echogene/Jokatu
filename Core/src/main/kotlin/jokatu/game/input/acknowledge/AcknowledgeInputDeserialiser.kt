package jokatu.game.input.acknowledge

import jokatu.game.input.DeserialisationException
import jokatu.game.input.TypedSingleKeyInputDeserialiser
import org.springframework.stereotype.Component

@Component
class AcknowledgeInputDeserialiser : TypedSingleKeyInputDeserialiser<Boolean, AcknowledgeInput>(Boolean::class, "acknowledge") {
	@Throws(DeserialisationException::class)
	override fun deserialiseTypedSingleValue(json: Map<String, Any>, value: Boolean): AcknowledgeInput {
		return AcknowledgeInput(value)
	}
}
