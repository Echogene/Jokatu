package jokatu.game.games.uzta.input;

import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.input.Input;
import ophelia.collections.bag.BaseIntegerBag;
import org.jetbrains.annotations.NotNull;

public class FullPlayerTradeRequest implements Input {

	private final BaseIntegerBag<NodeType> wantedResources;

	private final String playerName;

	public FullPlayerTradeRequest(@NotNull BaseIntegerBag<NodeType> wantedResources, @NotNull String playerName) {
		this.wantedResources = wantedResources;
		this.playerName = playerName;
	}
}
