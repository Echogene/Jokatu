package jokatu.game.games.uzta.player;

import jokatu.game.event.GameEvent;
import jokatu.game.games.uzta.event.ResourcesUpdatedEvent;
import jokatu.game.games.uzta.game.UztaColour;
import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.player.Player;
import ophelia.collections.bag.BaseIntegerBag;
import ophelia.collections.bag.HashBag;
import ophelia.collections.bag.ModifiableIntegerBag;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UztaPlayer extends AbstractSynchronousObservable<GameEvent> implements Player {

	private final String name;
	private UztaColour colour;

	private ModifiableIntegerBag<NodeType> resources = new HashBag<>();

	public UztaPlayer(@NotNull String username) {
		this.name = username;
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
		fireEvent(new ResourcesUpdatedEvent(this, type, number));
	}

	@NotNull
	public BaseIntegerBag<NodeType> getResourcesLeftAfter(@NotNull BaseIntegerBag<NodeType> requiredResources) {
		return resources.getDifference(requiredResources);
	}

	@NotNull
	@Override
	public String getName() {
		return name;
	}
}
