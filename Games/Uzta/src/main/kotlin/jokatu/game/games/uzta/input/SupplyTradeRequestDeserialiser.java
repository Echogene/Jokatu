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
public class SupplyTradeRequestDeserialiser extends InputDeserialiser<SupplyTradeRequest> {
	@NotNull
	@Override
	public SupplyTradeRequest deserialise(@NotNull Map<String, Object> json) throws DeserialisationException {
		ModifiableIntegerBag<NodeType> trade = Arrays.stream(NodeType.values())
				.map(n -> new Pair<>(n, (Integer) json.getOrDefault(n.toString(), 0)))
				.collect(toBag(Pair::getLeft, Pair::getRight));
		return new SupplyTradeRequest(trade);
	}
}
