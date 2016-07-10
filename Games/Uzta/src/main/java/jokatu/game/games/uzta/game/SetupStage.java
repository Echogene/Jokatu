package jokatu.game.games.uzta.game;

import jokatu.game.MultiInputStage;
import jokatu.game.games.uzta.graph.ModifiableUztaGraph;
import jokatu.game.input.EndStageInputAcceptor;
import jokatu.game.player.StandardPlayer;
import ophelia.collections.set.UnmodifiableSet;

import java.util.Map;

/**
 * The stage of {@link Uzta} where the game is set up.
 */
public class SetupStage extends MultiInputStage {

	private final RandomiseGraphInputAcceptor randomiseGraphInputAcceptor;

	SetupStage(ModifiableUztaGraph graph, Map<String, StandardPlayer> players) {
		randomiseGraphInputAcceptor = new RandomiseGraphInputAcceptor(graph);
		addInputAcceptor(randomiseGraphInputAcceptor);

		addInputAcceptor(new EndStageInputAcceptor<>(StandardPlayer.class, new UnmodifiableSet<>(players.values())));
	}

	@Override
	public void start() {
		randomiseGraphInputAcceptor.randomiseGraph();
	}
}
