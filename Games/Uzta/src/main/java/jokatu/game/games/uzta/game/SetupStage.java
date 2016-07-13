package jokatu.game.games.uzta.game;

import jokatu.game.MultiInputStage;
import jokatu.game.games.uzta.graph.ModifiableUztaGraph;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.EndStageInputAcceptor;
import ophelia.collections.set.UnmodifiableSet;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * The stage of {@link Uzta} where the game is set up.
 */
public class SetupStage extends MultiInputStage {

	private final RandomiseGraphInputAcceptor randomiseGraphInputAcceptor;

	SetupStage(@NotNull ModifiableUztaGraph graph, @NotNull Map<String, UztaPlayer> players) {
		randomiseGraphInputAcceptor = new RandomiseGraphInputAcceptor(graph);
		addInputAcceptor(randomiseGraphInputAcceptor);

		addInputAcceptor(new EndStageInputAcceptor<>(UztaPlayer.class, new UnmodifiableSet<>(players.values())));
	}

	@Override
	public void start() {
		randomiseGraphInputAcceptor.randomiseGraph();
	}
}
