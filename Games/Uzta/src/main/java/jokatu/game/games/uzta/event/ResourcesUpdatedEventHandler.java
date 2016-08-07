package jokatu.game.games.uzta.event;

import jokatu.game.Game;
import jokatu.game.event.SpecificEventHandler;
import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.games.uzta.player.UztaPlayer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class ResourcesUpdatedEventHandler extends SpecificEventHandler<ResourcesUpdatedEvent> {
	@NotNull
	@Override
	protected Class<ResourcesUpdatedEvent> getEventClass() {
		return ResourcesUpdatedEvent.class;
	}

	@Override
	protected void handleCastEvent(@NotNull Game<?> game, @NotNull ResourcesUpdatedEvent event) {
		UztaPlayer player = event.getPlayer();

		for (NodeType type : NodeType.values()) {
			sender.send(
					MessageFormat.format(
							"/topic/resource.game.{0}.{1}.{2}",
							game.getIdentifier(),
							player.getName(),
							type.toString()
					),
					player.getNumberOfType(type)
			);
		}
	}
}
