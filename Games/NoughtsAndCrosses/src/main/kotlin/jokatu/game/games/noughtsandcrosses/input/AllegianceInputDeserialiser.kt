package jokatu.game.games.noughtsandcrosses.input

import jokatu.game.input.DeserialisationException
import jokatu.game.input.TypedSingleKeyInputDeserialiser
import org.springframework.stereotype.Component

@Component
class AllegianceInputDeserialiser : TypedSingleKeyInputDeserialiser<String, AllegianceInput>(String::class, "allegiance") {
	@Throws(DeserialisationException::class)
	override fun deserialiseTypedSingleValue(json: Map<String, Any>, allegiance: String): AllegianceInput {
		val noughtOrCross = NoughtOrCross.displayValueOf(allegiance)
		return AllegianceInput(noughtOrCross)
	}
}
