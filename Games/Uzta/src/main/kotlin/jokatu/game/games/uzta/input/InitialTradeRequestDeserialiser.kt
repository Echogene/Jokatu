package jokatu.game.games.uzta.input

import jokatu.game.games.uzta.graph.NodeType
import jokatu.game.games.uzta.input.InitialTradeRequest.Companion.SUPPLY
import jokatu.game.input.DeserialisationException
import jokatu.game.input.InputDeserialiser
import org.springframework.stereotype.Component
import java.util.Arrays.stream
import java.util.stream.Collectors.joining

@Component
class InitialTradeRequestDeserialiser : InputDeserialiser<InitialTradeRequest>() {
	@Throws(DeserialisationException::class)
	override fun deserialise(json: Map<String, Any>): InitialTradeRequest {
		val resource = getResource(json)

		return if (json.containsKey("player")) {
			val playerName = getMandatoryKeyValueOfType(String::class.java, "player", json)
			InitialTradeRequest(playerName, resource)
		} else {
			InitialTradeRequest(SUPPLY, resource)
		}
	}

	@Throws(DeserialisationException::class)
	private fun getResource(json: Map<String, Any>): NodeType {
		val resourceName = getMandatoryKeyValueOfType(String::class.java, "resource", json)
		val resource: NodeType
		try {
			resource = NodeType.valueOf(resourceName)
		} catch (e: IllegalArgumentException) {
			val nodeTypes = stream(NodeType.values())
					.map({ it.toString() })
					.collect(joining(", ", "[", "]"))
			throw DeserialisationException(
					json,
					"$resourceName was not a valid node type.  Expected one of $nodeTypes."
			)
		}

		return resource
	}
}
