package jokatu.game.games.uzta.input;

import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.input.DeserialisationException;
import jokatu.game.input.InputDeserialiser;
import ophelia.collections.bag.ModifiableIntegerBag;
import ophelia.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

import static ophelia.collections.bag.BagCollectors.toBag;

@Component
public class FullPlayerTradeRequestDeserialiser extends InputDeserialiser<FullPlayerTradeRequest> {
	@NotNull
	@Override
	public FullPlayerTradeRequest deserialise(@NotNull Map<String, Object> json) throws DeserialisationException {
		ModifiableIntegerBag<NodeType> trade = Arrays.stream(NodeType.values())
				.map(n -> new Pair<>(n, (Integer) json.getOrDefault(n.toString(), 0)))
				.collect(toBag(Pair::getLeft, Pair::getRight));

		String playerName = getMandatoryKeyValueOfType(String.class, "player", json);
		return new FullPlayerTradeRequest(trade, playerName);
	}
}
