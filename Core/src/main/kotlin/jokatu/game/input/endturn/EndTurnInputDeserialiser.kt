package jokatu.game.input.endturn

import jokatu.game.input.DeserialisationException
import jokatu.game.input.SingleKeyInputDeserialiser
import org.springframework.stereotype.Component

@Component
class EndTurnInputDeserialiser : SingleKeyInputDeserialiser<EndTurnInput>() {

	override val keyName: String
		get() = "skip"

	@Throws(DeserialisationException::class)
	override fun deserialiseSingleValue(json: Map<String, Any>, value: Any): EndTurnInput {
		return EndTurnInput()
	}
}
