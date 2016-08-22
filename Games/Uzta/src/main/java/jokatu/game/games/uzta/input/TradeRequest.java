package jokatu.game.games.uzta.input;

import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.input.Input;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TradeRequest implements Input {

	public static final String SUPPLY = null;

	private final String playerName;
	private final NodeType resource;

	public TradeRequest(@Nullable String playerName, @NotNull NodeType resource) {
		this.playerName = playerName;
		this.resource = resource;
	}

	@Nullable
	public String getPlayerName() {
		return playerName;
	}

	@NotNull
	public NodeType getResource() {
		return resource;
	}
}
