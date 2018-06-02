package jokatu.game.games.uzta.input

import jokatu.game.games.uzta.graph.NodeType
import jokatu.game.input.DeserialisationException
import jokatu.game.input.InputDeserialiser
import ophelia.collections.bag.BagCollectors.toBag
import ophelia.tuple.Pair
import org.springframework.stereotype.Component
import java.util.*

@Component
class FullPlayerTradeRequestDeserialiser : InputDeserialiser<FullPlayerTradeRequest>() {
	@Throws(DeserialisationException::class)
	override fun deserialise(json: Map<String, Any>): FullPlayerTradeRequest {
		val trade = Arrays.stream(NodeType.values())
				.map { n -> Pair(n, json.getOrDefault(n.toString(), 0) as Int) }
				.collect(toBag<Pair<NodeType, Int>, NodeType>({ it.left }, { it.right }))

		val playerName = getMandatoryKeyValueOfType(String::class, "player", json)
		return FullPlayerTradeRequest(trade, playerName)
	}
}
