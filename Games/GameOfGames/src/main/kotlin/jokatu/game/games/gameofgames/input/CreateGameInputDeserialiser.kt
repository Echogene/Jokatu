package jokatu.game.games.gameofgames.input

import jokatu.game.input.DeserialisationException
import jokatu.game.input.TypedSingleKeyInputDeserialiser
import org.springframework.stereotype.Component

@Component
class CreateGameInputDeserialiser : TypedSingleKeyInputDeserialiser<String, CreateGameInput>() {

	override val type: Class<String>
		get() = String::class.java

	override val keyName: String
		get() = "gameName"

	@Throws(DeserialisationException::class)
	override fun deserialiseTypedSingleValue(json: Map<String, Any>, gameName: String): CreateGameInput {
		return CreateGameInput(gameName)
	}
}