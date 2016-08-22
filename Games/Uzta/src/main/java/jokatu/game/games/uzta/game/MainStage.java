package jokatu.game.games.uzta.game;

import jokatu.game.MultiInputStage;
import jokatu.game.games.uzta.graph.UztaGraph;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.endturn.EndTurnInputAcceptor;
import jokatu.game.status.StandardTextStatus;
import jokatu.game.turn.TurnManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainStage extends MultiInputStage {

	private final ResourceDistributor resourceDistributor;
	private final TurnManager<UztaPlayer> turnManager;
	private final StandardTextStatus status;

	MainStage(@NotNull UztaGraph graph, @NotNull List<UztaPlayer> playersInOrder, @NotNull StandardTextStatus status) {
		resourceDistributor = new ResourceDistributor(graph);
		turnManager = new TurnManager<>(playersInOrder);
		this.status = status;

		MainStageSelectEdgeInputAcceptor mainStageSelectEdgeInputAcceptor = new MainStageSelectEdgeInputAcceptor(graph, turnManager, resourceDistributor);
		addInputAcceptor(mainStageSelectEdgeInputAcceptor);

		TradeRequestAcceptor tradeRequestAcceptor = new TradeRequestAcceptor();
		addInputAcceptor(tradeRequestAcceptor);

		EndTurnInputAcceptor<UztaPlayer> endTurnInputAcceptor = new EndTurnInputAcceptor<>(turnManager, UztaPlayer.class);
		addInputAcceptor(endTurnInputAcceptor);

		turnManager.observe(e -> {
			status.setText("Waiting for {0} to buy edges or pass.", e.getNewPlayer());
			// Forward the event.
			fireEvent(e);
		});

		playersInOrder.forEach(player -> player.observe(this::fireEvent));
	}

	@Override
	public void start() {
		resourceDistributor.distributeStartingResources();

		turnManager.next();
	}
}
