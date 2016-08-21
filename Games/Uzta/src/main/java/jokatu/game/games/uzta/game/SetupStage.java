package jokatu.game.games.uzta.game;

import jokatu.game.MultiInputStage;
import jokatu.game.games.uzta.graph.ModifiableUztaGraph;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.EndStageInputAcceptor;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.set.UnmodifiableSet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * The stage of {@link Uzta} where the game is set up.
 */
public class SetupStage extends MultiInputStage {

	private final RandomiseGraphInputAcceptor randomiseGraphInputAcceptor;
	private final StandardTextStatus status;

	SetupStage(@NotNull ModifiableUztaGraph graph, @NotNull Map<String, UztaPlayer> players, @NotNull StandardTextStatus status) {
		randomiseGraphInputAcceptor = new RandomiseGraphInputAcceptor(graph);
		this.status = status;
		addInputAcceptor(randomiseGraphInputAcceptor);

		addInputAcceptor(new EndStageInputAcceptor<>(UztaPlayer.class, new UnmodifiableSet<>(players.values())));

		assignColours(players.values());
	}

	private void assignColours(Collection<UztaPlayer> players) {
		List<UztaPlayer> orderedPlayers = new ArrayList<>(players);
		IntStream.range(0, orderedPlayers.size())
				.forEach(i -> orderedPlayers.get(i).setColour(UztaColour.values()[i]));
	}

	@Override
	public void start() {
		randomiseGraphInputAcceptor.randomiseGraph();

		status.setText("Waiting for a player to accept a random graph.");
	}
}
