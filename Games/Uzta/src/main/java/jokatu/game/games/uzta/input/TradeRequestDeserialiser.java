package jokatu.game.games.uzta.input;

import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.input.DeserialisationException;
import jokatu.game.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static jokatu.game.games.uzta.input.TradeRequest.SUPPLY;

@Component
public class TradeRequestDeserialiser extends InputDeserialiser<TradeRequest> {
	@NotNull
	@Override
	public TradeRequest deserialise(@NotNull Map<String, Object> json) throws DeserialisationException {
		NodeType resource = getResource(json);

		if (json.containsKey("player")) {
			String playerName = getMandatoryKeyValueOfType(String.class, "player", json);
			return new TradeRequest(playerName, resource);
		} else {
			return new TradeRequest(SUPPLY, resource);
		}
	}

	@NotNull
	private NodeType getResource(@NotNull Map<String, Object> json) throws DeserialisationException {
		String resourceName = getMandatoryKeyValueOfType(String.class, "resource", json);
		NodeType resource;
		try {
			resource = NodeType.valueOf(resourceName);
		} catch (IllegalArgumentException e) {
			throw new DeserialisationException(
					json,
					MessageFormat.format(
							"{0} was not a valid node type.  Expected one of {1}.",
							resourceName,
							stream(NodeType.values())
									.map(Enum::toString)
									.collect(joining(", ", "[", "]"))
					)
			);
		}
		return resource;
	}
}
