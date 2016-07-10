package jokatu.game.games.uzta.game;

import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.Node;
import jokatu.game.games.uzta.graph.UztaGraph;
import jokatu.game.games.uzta.input.SelectEdgeInput;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.stage.AnyEventSingleInputStage;
import jokatu.game.turn.TurnManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The stage where players determine their starting positions.
 */
public class FirstPlacementStage extends AnyEventSingleInputStage<SelectEdgeInput, UztaPlayer> {


	private final UztaGraph graph;
	private final List<UztaPlayer> players;
	private final TurnManager<UztaPlayer> turnManager;

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

		// todo: give ownership of the edge to the player

		turnManager.next();
	}
}
