package jokatu.game.games.uzta.game;

import jokatu.game.event.GameEvent;
import jokatu.game.event.StageOverEvent;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.UztaGraph;
import jokatu.game.games.uzta.input.SelectEdgeInput;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.stage.Stage;
import jokatu.game.turn.TurnManager;
import ophelia.collections.list.UnmodifiableList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptySet;

/**
 * The stage where players determine their starting positions.
 */
public class FirstPlacementStage extends AbstractSelectEdgeInputAcceptor implements Stage<GameEvent> {

	private final UnmodifiableList<UztaPlayer> players;

	FirstPlacementStage(@NotNull UztaGraph graph, @NotNull List<UztaPlayer> playersInOrder) {
		super(graph, getTurnManager(playersInOrder));
		this.players = new UnmodifiableList<>(playersInOrder);

		this.turnManager.observe(this::fireEvent);
	}

	@NotNull
	private static TurnManager<UztaPlayer> getTurnManager(@NotNull List<UztaPlayer> players) {
		return new TurnManager<>(players);
	}

	@Override
	public void start() {
		turnManager.next();
	}

	@NotNull
	UnmodifiableList<UztaPlayer> getPlayersInOrder() {
		return players;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull SelectEdgeInput input, @NotNull UztaPlayer inputter) throws Exception {
		LineSegment edge = getUnownedLineSegment(input, inputter);

		Set<LineSegment> alreadyOwnedEdges = ownedEdgesPerPlayer.getOrDefault(inputter, emptySet());
		if (alreadyOwnedEdges.size() > 1) {
			throw new UnacceptableInputException("You already own more than one edge.  Don't be greedy!");
		}

		setOwner(edge, inputter);

		Set<LineSegment> ownedEdges = getNotNullOwnedEdgesForPlayer(inputter);
		int minOwnedEdges = players.stream()
				.map(player -> ownedEdgesPerPlayer.getOrDefault(player, emptySet()))
				.mapToInt(Set::size)
				.min()
				.orElse(0);
		if (minOwnedEdges > 1) {
			// Everyone now owns two edges.
			fireEvent(new StageOverEvent());
		} else if (ownedEdges.size() < 2) {
			if (minOwnedEdges > 0) {
				// Everyone has input once, so go again.
				turnManager.playAgain();
			} else {
				turnManager.next();
			}
		} else {
			turnManager.previous();
		}
	}
}
