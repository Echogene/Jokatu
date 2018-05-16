package jokatu.game.games.echo.game

import jokatu.game.games.echo.input.EchoInput
import jokatu.game.input.TypedSingleKeyInputDeserialiser
import org.springframework.stereotype.Component

@Component
class EchoInputDeserialiser : TypedSingleKeyInputDeserialiser<String, EchoInput>(String::class.java) {

	override val keyName: String
		get() = "text"

	override fun deserialiseTypedSingleValue(json: Map<String, Any>, value: String): EchoInput {
		return EchoInput(value)
	}
}
