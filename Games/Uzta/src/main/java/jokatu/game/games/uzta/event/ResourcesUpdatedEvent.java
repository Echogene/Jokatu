package jokatu.game.games.uzta.event;

import jokatu.game.event.PublicGameEvent;
import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.games.uzta.player.UztaPlayer;
import ophelia.collections.bag.BaseIntegerBag;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class ResourcesUpdatedEvent implements PublicGameEvent {
	private final UztaPlayer player;
	private final BaseIntegerBag<NodeType> resources;

	public ResourcesUpdatedEvent(@NotNull UztaPlayer player, @NotNull BaseIntegerBag<NodeType> resources) {
		this.player = player;
		this.resources = resources;
	}

	@NotNull
	@Override
	public String getMessage() {
		List<String> messages = new ArrayList<>();
		if (resources.hasItems()) {
			messages.add(MessageFormat.format(
					"gained {0}",
					resources.stream()
							.filter(entry -> entry.getRight() > 0)
							.map(entry -> entry.getLeft().getNumber(entry.getRight()))
							.collect(joining(", "))
			));
		}
		if (resources.isLacking()) {
			messages.add(MessageFormat.format(
					"paid {0}",
					resources.stream()
							.filter(entry -> entry.getRight() < 0)
							.map(entry -> entry.getLeft().getNumber(entry.getRight()))
							.collect(joining(", "))
			));
		}

		return player.toString() + messages.stream().collect(joining(" and ", " ", ""));
	}

	@NotNull
	public UztaPlayer getPlayer() {
		return player;
	}
}
