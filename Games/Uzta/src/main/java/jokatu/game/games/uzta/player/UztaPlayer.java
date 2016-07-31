package jokatu.game.games.uzta.player;

import jokatu.game.games.uzta.game.UztaColour;
import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.player.StandardPlayer;
import ophelia.collections.bag.BaseIntegerBag;
import ophelia.collections.bag.HashBag;
import ophelia.collections.bag.ModifiableIntegerBag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UztaPlayer extends StandardPlayer {
	private UztaColour colour;

	private ModifiableIntegerBag<NodeType> resources = new HashBag<>();

	public UztaPlayer(String username) {
		super(username);
	}

	public void setColour(@Nullable UztaColour colour) {
		this.colour = colour;
	}

	@Nullable
	public UztaColour getColour() {
		return colour;
	}

	public void giveResources(@NotNull NodeType type, int number) {
		resources.modifyNumberOf(type, number);
	}

	@NotNull
	public BaseIntegerBag<NodeType> getResourcesLeftAfter(@NotNull BaseIntegerBag<NodeType> requiredResources) {
		return resources.getDifference(requiredResources);
	}
}
