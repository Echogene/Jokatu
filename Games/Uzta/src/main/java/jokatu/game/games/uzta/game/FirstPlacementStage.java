package jokatu.game.games.uzta.game;

import jokatu.game.event.StageOverEvent;
import jokatu.game.games.uzta.event.GraphUpdatedEvent;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.Node;
import jokatu.game.games.uzta.graph.UztaGraph;
import jokatu.game.games.uzta.input.SelectEdgeInput;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.stage.AnyEventSingleInputStage;
import jokatu.game.turn.TurnManager;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static ophelia.util.MapUtils.updateSetBasedMap;

/**
 * The stage where players determine their starting positions.
 */
public class FirstPlacementStage extends AnyEventSingleInputStage<SelectEdgeInput, UztaPlayer> {


	private final UztaGraph graph;
	private final List<UztaPlayer> players;
	private final TurnManager<UztaPlayer> turnManager;

	private final Map<UztaPlayer, Set<LineSegment>> ownedEdgesPerPlayer = new HashMap<>();

	FirstPlacementStage(@NotNull UztaGraph graph, @NotNull Map<String, UztaPlayer> players) {
		this.graph = graph;
		this.players = determineTurnOrder(players);

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

	@Override
	public void start() {
		turnManager.next();
	}

	@NotNull
	private List<UztaPlayer> determineTurnOrder(Map<String, UztaPlayer> players) {
		return new ArrayList<>(players.values());
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull SelectEdgeInput input, @NotNull UztaPlayer inputter) throws Exception {
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

		Set<LineSegment> alreadyOwnedEdges = ownedEdgesPerPlayer.get(inputter);
		if (alreadyOwnedEdges.size() > 1) {
			throw new UnacceptableInputException("You already own more than one edge.  Don't be greedy!");
		}

		edge.setOwner(inputter);
		updateSetBasedMap(ownedEdgesPerPlayer, inputter, edge);
		fireEvent(new GraphUpdatedEvent(graph));

		Set<LineSegment> ownedEdges = ownedEdgesPerPlayer.get(inputter);
		if (ownedEdges.size() == 1) {
			turnManager.next();
		} else {
			turnManager.previous();
		}

		int min = ownedEdgesPerPlayer.values().stream()
				.mapToInt(Set::size)
				.min()
				.orElse(0);

		if (min > 1) {
			// Everyone now owns two edges.
			fireEvent(new StageOverEvent());
		}
	}
}
