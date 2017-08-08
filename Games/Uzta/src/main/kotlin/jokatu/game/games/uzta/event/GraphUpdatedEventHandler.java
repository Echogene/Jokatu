package jokatu.game.games.uzta.event;

import jokatu.game.Game;
import jokatu.game.event.SpecificEventHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class GraphUpdatedEventHandler extends SpecificEventHandler<GraphUpdatedEvent> {
	@NotNull
	@Override
	protected Class<GraphUpdatedEvent> getEventClass() {
		return GraphUpdatedEvent.class;
	}

	@Override
	protected void handleCastEvent(@NotNull Game<?> game, @NotNull GraphUpdatedEvent event) {
		sender.send("/topic/graph.game." + game.getIdentifier(), event.getGraph());
	}
}
