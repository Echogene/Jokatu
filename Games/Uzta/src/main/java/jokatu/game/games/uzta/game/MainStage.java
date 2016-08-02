package jokatu.game.games.uzta.game;

import jokatu.game.MultiInputStage;
import jokatu.game.games.uzta.graph.UztaGraph;
import jokatu.game.games.uzta.player.UztaPlayer;

import java.util.Map;

public class MainStage extends MultiInputStage {

	private final MainStageSelectEdgeInputAcceptor mainStageSelectEdgeInputAcceptor;

	MainStage(UztaGraph graph, Map<String, UztaPlayer> players) {
		mainStageSelectEdgeInputAcceptor = new MainStageSelectEdgeInputAcceptor(graph, players);
		addInputAcceptor(mainStageSelectEdgeInputAcceptor);
	}

	@Override
	public void start() {
		mainStageSelectEdgeInputAcceptor.distributeStartingResources();
		mainStageSelectEdgeInputAcceptor.startNextTurn();
	}
}
