package jokatu.game.joining

import jokatu.game.input.DeserialisationException
import jokatu.game.input.SingleKeyInputDeserialiser
import org.springframework.stereotype.Component

/**
 * Deserialises [JoinInput]s.  The correct JSON should look like `{"join": true}`.
 */
@Component
class JoinInputDeserialiser : SingleKeyInputDeserialiser<JoinInput>() {

	override val keyName: String
		get() = "join"

	@Throws(DeserialisationException::class)
	override fun deserialiseSingleValue(json: Map<String, Any>, value: Any): JoinInput {
		return JoinInput()
	}
}
