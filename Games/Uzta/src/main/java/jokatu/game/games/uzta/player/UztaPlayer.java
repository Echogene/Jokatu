package jokatu.game.games.uzta.player;

import jokatu.game.event.GameEvent;
import jokatu.game.games.uzta.event.ResourcesUpdatedEvent;
import jokatu.game.games.uzta.game.UztaColour;
import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.player.Player;
import ophelia.collections.bag.BagUtils;
import ophelia.collections.bag.BaseIntegerBag;
import ophelia.collections.bag.HashBag;
import ophelia.collections.bag.ModifiableIntegerBag;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;

import static java.util.stream.Collectors.joining;

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

	public void giveResources(@NotNull BaseIntegerBag<NodeType> givenResources) {
		if (resources.getSum(givenResources).hasLackingItems()) {
			throw new NotEnoughResourcesException(
					"{0} does not have enough resources {1}",
					name,
					BagUtils.presentBag(
							givenResources,
							NodeType::getNumber,
							joining(", ", "to be given ", " "),
							joining(", ", "and to give ", ".")
					)
			);
		}
		resources.merge(givenResources);
		fireEvent(new ResourcesUpdatedEvent(this, givenResources));
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

	@Override
	public String toString() {
		return name;
	}

	public int getNumberOfType(@NotNull NodeType type) {
		return resources.getNumberOf(type);
	}

	public static class NotEnoughResourcesException extends RuntimeException {
		public NotEnoughResourcesException(@NotNull String pattern, Object... arguments) {
			super(MessageFormat.format(pattern, arguments));
		}
	}
}
