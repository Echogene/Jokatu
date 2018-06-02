package jokatu.game.input.finishstage

import jokatu.game.input.DeserialisationException
import jokatu.game.input.SingleKeyInputDeserialiser
import org.springframework.stereotype.Component

/**
 * Deserialises [EndStageInput]s.  The correct JSON should look like `{"end": true}`.
 */
@Component
class EndStageInputDeserialiser : SingleKeyInputDeserialiser<EndStageInput>("end") {
	@Throws(DeserialisationException::class)
	override fun deserialiseSingleValue(json: Map<String, Any>, value: Any): EndStageInput {
		return EndStageInput()
	}
}
