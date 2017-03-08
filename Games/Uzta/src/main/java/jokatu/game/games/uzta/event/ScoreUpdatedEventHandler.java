package jokatu.game.games.uzta.event;

import jokatu.game.event.AbstractEventHandler;
import jokatu.game.games.uzta.game.Uzta;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.UztaGraph;
import jokatu.game.games.uzta.player.UztaPlayer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

import static java.util.function.Predicate.isEqual;

/**
 * When the {@link GraphUpdatedEvent graph is updated}, update each player's score.
 */
@Component
public class ScoreUpdatedEventHandler extends AbstractEventHandler<Uzta, GraphUpdatedEvent> {
	@NotNull
	@Override
	protected Class<GraphUpdatedEvent> getEventClass() {
		return GraphUpdatedEvent.class;
	}

	@NotNull
	@Override
	protected Class<Uzta> getGameClass() {
		return Uzta.class;
	}

	@Override
	protected void handleCastGameAndEvent(
			@NotNull Uzta game, @NotNull GraphUpdatedEvent event
	) {
		game.getPlayers().forEach(
				player -> sender.send(
						MessageFormat.format(
								"/topic/score.game.{0}.{1}",
								game.getIdentifier(),
								player.getName()
						),
						calculateScore(player, game.getGraph())
				)
		);
	}

	private long calculateScore(@NotNull UztaPlayer player, @NotNull UztaGraph graph) {
		return graph.getEdges().stream()
				.map(LineSegment::getOwner)
				.filter(isEqual(player))
				.count();
	}
}
