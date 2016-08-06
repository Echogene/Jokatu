package jokatu.game.games.uzta.game;

import jokatu.game.event.GameEvent;
import jokatu.game.games.uzta.event.GraphUpdatedEvent;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.Node;
import jokatu.game.games.uzta.graph.UztaGraph;
import jokatu.game.games.uzta.input.SelectEdgeInput;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.AbstractInputAcceptor;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.turn.TurnManager;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static ophelia.util.MapUtils.updateSetBasedMap;

public abstract class AbstractSelectEdgeInputAcceptor extends AbstractInputAcceptor<SelectEdgeInput, UztaPlayer, GameEvent> {
	protected final UztaGraph graph;
	protected final TurnManager<UztaPlayer> turnManager;
	protected final Map<UztaPlayer, Set<LineSegment>> ownedEdgesPerPlayer = new HashMap<>();

	protected AbstractSelectEdgeInputAcceptor(@NotNull UztaGraph graph, @NotNull TurnManager<UztaPlayer> turnManager) {
		this.graph = graph;

		this.turnManager = turnManager;

		graph.getEdges().stream()
				.filter(edge -> edge.getOwner() != null)
				.forEach(edge -> updateSetBasedMap(ownedEdgesPerPlayer, edge.getOwner(), edge));
	}


	@NotNull
	@Override
	protected Class<SelectEdgeInput> getInputClass() {
		return SelectEdgeInput.class;
	}

	@NotNull
	@Override
	protected Class<UztaPlayer> getPlayerClass() {
		return UztaPlayer.class;
	}

	@NotNull
	protected LineSegment getUnownedLineSegment(@NotNull SelectEdgeInput input, @NotNull UztaPlayer inputter) throws Exception {
		turnManager.assertCurrentPlayer(inputter);

		String startId = input.getStartId();
		Node start = graph.getNode(startId)
				.orElseThrow(() -> new UnacceptableInputException("Could not find node with id ''{0}''", startId));

		String endId = input.getEndId();
		Node end = graph.getNode(endId)
				.orElseThrow(() -> new UnacceptableInputException("Could not find node with id ''{0}''", endId));

		LineSegment edge = graph.getEdge(start, end)
				.orElseThrow(() -> new UnacceptableInputException("Could not find edge between {0} and {1}", start, end));

		if (edge.getOwner() != null) {
			if (edge.getOwner() == inputter) {
				throw new UnacceptableInputException("You already own that edge!");
			} else {
				throw new UnacceptableInputException("{0} already owns this edge.", edge.getOwner().getName());
			}
		}
		return edge;
	}

	protected void setOwner(LineSegment edge, @NotNull UztaPlayer inputter) {
		edge.setOwner(inputter);
		updateSetBasedMap(ownedEdgesPerPlayer, inputter, edge);
		fireEvent(new GraphUpdatedEvent(graph));
	}

	@NotNull
	protected Set<LineSegment> getNotNullOwnedEdgesForPlayer(@NotNull UztaPlayer inputter) {
		Set<LineSegment> ownedEdges = ownedEdgesPerPlayer.get(inputter);
		if (ownedEdges == null) {
			throw new IllegalStateException("You should own at least one edge by this point.");
		}
		return ownedEdges;
	}
}
