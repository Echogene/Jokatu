package jokatu.game.games.uzta.input;

import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.input.Input;
import ophelia.collections.bag.BaseIntegerBag;
import org.jetbrains.annotations.NotNull;

public class FullPlayerTradeRequest implements Input {

	private final BaseIntegerBag<NodeType> wantedResources;

	private final BaseIntegerBag<NodeType> givenResources;

	private final String playerName;

	public FullPlayerTradeRequest(
			@NotNull BaseIntegerBag<NodeType> wantedResources,
			@NotNull BaseIntegerBag<NodeType> givenResources,
			@NotNull String playerName
	) {
		this.wantedResources = wantedResources;
		this.givenResources = givenResources;
		this.playerName = playerName;
	}

	@NotNull
	public BaseIntegerBag<NodeType> getWantedResources() {
		return wantedResources;
	}

	@NotNull
	public BaseIntegerBag<NodeType> getGivenResources() {
		return givenResources;
	}

	@NotNull
	public String getPlayerName() {
		return playerName;
	}
}
