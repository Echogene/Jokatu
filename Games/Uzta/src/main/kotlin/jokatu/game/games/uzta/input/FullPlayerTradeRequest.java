package jokatu.game.games.uzta.input;

import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.input.Input;
import ophelia.collections.bag.BaseIntegerBag;
import org.jetbrains.annotations.NotNull;

public class FullPlayerTradeRequest implements Input {

	private final BaseIntegerBag<NodeType> trade;

	private final String playerName;

	public FullPlayerTradeRequest(
			@NotNull BaseIntegerBag<NodeType> trade,
			@NotNull String playerName
	) {
		this.trade = trade;
		this.playerName = playerName;
	}

	@NotNull
	public BaseIntegerBag<NodeType> getTrade() {
		return trade;
	}

	@NotNull
	public String getPlayerName() {
		return playerName;
	}
}
