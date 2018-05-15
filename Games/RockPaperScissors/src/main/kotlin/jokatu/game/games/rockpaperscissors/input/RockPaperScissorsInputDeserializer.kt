package jokatu.game.games.rockpaperscissors.input

import jokatu.game.games.rockpaperscissors.game.RockPaperScissors
import jokatu.game.input.TypedSingleKeyInputDeserialiser
import org.springframework.stereotype.Component

@Component
class RockPaperScissorsInputDeserializer : TypedSingleKeyInputDeserialiser<String, RockPaperScissorsInput>() {

	override val keyName: String
		get() = "choice"

	override val type: Class<String>
		get() = String::class.java

	override fun deserialiseTypedSingleValue(json: Map<String, Any>, choice: String): RockPaperScissorsInput {
		return RockPaperScissorsInput(RockPaperScissors.valueOf(choice))
	}
}
