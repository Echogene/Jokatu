package jokatu.game.games.uzta.game;

import jokatu.game.MultiInputStage;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.Node;

import java.util.List;
import java.util.Set;

/**
 * The stage of {@link Uzta} where the game is set up.
 */
public class SetupStage extends MultiInputStage {

	private final RandomiseGraphInputAcceptor randomiseGraphInputAcceptor;

	SetupStage(List<Node> nodes, Set<LineSegment> edges) {
		randomiseGraphInputAcceptor = new RandomiseGraphInputAcceptor(nodes, edges);
		addInputAcceptor(randomiseGraphInputAcceptor);
	}

	@Override
	public void start() {
		randomiseGraphInputAcceptor.randomiseGraph();
	}
}
