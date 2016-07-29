package jokatu.game.games.uzta.game;

import jokatu.game.event.GameEvent;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.Node;
import jokatu.game.games.uzta.graph.UztaGraph;
import jokatu.game.games.uzta.input.SelectEdgeInput;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.AbstractInputAcceptor;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.turn.TurnManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptySet;

public abstract class AbstractSelectEdgeInputAcceptor extends AbstractInputAcceptor<SelectEdgeInput, UztaPlayer, GameEvent> {
	protected final UztaGraph graph;
	protected final TurnManager<UztaPlayer> turnManager;
	protected final Map<UztaPlayer, Set<LineSegment>> ownedEdgesPerPlayer = new HashMap<>();
	protected final List<UztaPlayer> players;

	protected AbstractSelectEdgeInputAcceptor(@NotNull UztaGraph graph, List<UztaPlayer> players) {
		this.graph = graph;
		this.players = players;

		turnManager = new TurnManager<>(this.players);
		turnManager.observe(this::fireEvent);
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
	protected LineSegment getLineSegment(@NotNull SelectEdgeInput input, @NotNull UztaPlayer inputter) throws Exception {
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

		Set<LineSegment> alreadyOwnedEdges = ownedEdgesPerPlayer.getOrDefault(inputter, emptySet());
		if (alreadyOwnedEdges.size() > 1) {
			throw new UnacceptableInputException("You already own more than one edge.  Don't be greedy!");
		}
		return edge;
	}
}
