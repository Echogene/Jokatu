package jokatu.game.games.uzta.game;

import jokatu.game.MultiInputStage;
import jokatu.game.games.uzta.graph.UztaGraph;
import jokatu.game.games.uzta.player.UztaPlayer;

import java.util.Map;

public class MainStage extends MultiInputStage {

	private final MainStageSelectEdgeInputAcceptor mainStageSelectEdgeInputAcceptor;
	private final ResourceDistributor resourceDistributor;

	MainStage(UztaGraph graph, Map<String, UztaPlayer> players) {
		resourceDistributor = new ResourceDistributor(graph);

		mainStageSelectEdgeInputAcceptor = new MainStageSelectEdgeInputAcceptor(graph, players, resourceDistributor);
		addInputAcceptor(mainStageSelectEdgeInputAcceptor);

		players.values().forEach(player -> player.observe(this::fireEvent));
	}

	@Override
	public void start() {
		resourceDistributor.distributeStartingResources();

		mainStageSelectEdgeInputAcceptor.startNextTurn();
	}
}
