package jokatu.game.games.uzta.game;

import jokatu.game.MultiInputStage;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.Node;
import jokatu.game.input.EndStageInputAcceptor;
import jokatu.game.player.StandardPlayer;
import ophelia.collections.set.UnmodifiableSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The stage of {@link Uzta} where the game is set up.
 */
public class SetupStage extends MultiInputStage {

	private final RandomiseGraphInputAcceptor randomiseGraphInputAcceptor;

	SetupStage(List<Node> nodes, Set<LineSegment> edges, Map<String, StandardPlayer> players) {
		randomiseGraphInputAcceptor = new RandomiseGraphInputAcceptor(nodes, edges);
		addInputAcceptor(randomiseGraphInputAcceptor);

		addInputAcceptor(new EndStageInputAcceptor<>(StandardPlayer.class, new UnmodifiableSet<>(players.values())));
	}

	@Override
	public void start() {
		randomiseGraphInputAcceptor.randomiseGraph();
	}
}
