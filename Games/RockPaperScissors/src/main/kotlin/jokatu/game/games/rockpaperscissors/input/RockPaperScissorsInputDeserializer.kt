package jokatu.game.games.rockpaperscissors.input

import jokatu.game.games.rockpaperscissors.game.RockPaperScissors
import jokatu.game.input.TypedSingleKeyInputDeserialiser
import org.springframework.stereotype.Component

@Component
class RockPaperScissorsInputDeserializer : TypedSingleKeyInputDeserialiser<String, RockPaperScissorsInput>(String::class.java) {

	override val keyName: String
		get() = "choice"

	override fun deserialiseTypedSingleValue(json: Map<String, Any>, choice: String): RockPaperScissorsInput {
		return RockPaperScissorsInput(RockPaperScissors.valueOf(choice))
	}
}
