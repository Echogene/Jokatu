package jokatu.game.games.uzta.input;

import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.input.Input;
import ophelia.collections.bag.BaseIntegerBag;
import org.jetbrains.annotations.NotNull;

public class SupplyTradeRequest implements Input {

	@NotNull
	private final BaseIntegerBag<NodeType> trade;

	public SupplyTradeRequest(@NotNull BaseIntegerBag<NodeType> trade) {
		this.trade = trade;
	}

	@NotNull
	public BaseIntegerBag<NodeType> getTrade() {
		return trade;
	}
}
