package jokatu.game.games.uzta.event;

import jokatu.game.event.PublicGameEvent;
import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.games.uzta.player.UztaPlayer;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class ResourcesUpdatedEvent implements PublicGameEvent {
	private final UztaPlayer player;
	private final NodeType type;
	private final int number;

	public ResourcesUpdatedEvent(@NotNull UztaPlayer player, @NotNull NodeType type, int number) {
		this.player = player;
		this.type = type;
		this.number = number;
	}

	@NotNull
	@Override
	public String getMessage() {
		return MessageFormat.format("{0} received {1}", player.getName(), type.getNumber(number));
	}

	@NotNull
	public UztaPlayer getPlayer() {
		return player;
	}

	@NotNull
	public NodeType getType() {
		return type;
	}

	public int getNumber() {
		return number;
	}
}
