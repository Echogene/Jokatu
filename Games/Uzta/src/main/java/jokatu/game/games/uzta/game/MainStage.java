package jokatu.game.games.uzta.game;

import jokatu.game.MultiInputStage;
import jokatu.game.games.uzta.graph.UztaGraph;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.turn.TurnManager;

import java.util.List;

public class MainStage extends MultiInputStage {

	private final ResourceDistributor resourceDistributor;
	private final TurnManager<UztaPlayer> turnManager;

	MainStage(UztaGraph graph, List<UztaPlayer> playersInOrder) {
		resourceDistributor = new ResourceDistributor(graph);
		turnManager = new TurnManager<>(playersInOrder);

		MainStageSelectEdgeInputAcceptor mainStageSelectEdgeInputAcceptor = new MainStageSelectEdgeInputAcceptor(graph, turnManager, resourceDistributor);
		addInputAcceptor(mainStageSelectEdgeInputAcceptor);

		playersInOrder.forEach(player -> player.observe(this::fireEvent));
	}

	@Override
	public void start() {
		resourceDistributor.distributeStartingResources();

		turnManager.next();
	}
}
