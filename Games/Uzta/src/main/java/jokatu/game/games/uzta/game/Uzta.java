package jokatu.game.games.uzta.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.games.uzta.graph.ModifiableUztaGraph;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.stage.GameOverStage;
import jokatu.game.stage.MinAndMaxJoiningStage;
import jokatu.game.stage.machine.SequentialStageMachine;
import jokatu.game.status.StandardTextStatus;
import org.jetbrains.annotations.NotNull;

/**
 * A game played on an abstract graph where players harvest abstract resources produced by the graph in order to build
 * more things on the graph.
 */
public class Uzta extends Game<UztaPlayer> {

	public static final String UZTA = "Uzta";

	static final int DICE_SIZE = 12;

	private final ModifiableUztaGraph graph = new ModifiableUztaGraph();

	private final StandardTextStatus status = new StandardTextStatus();

	Uzta(GameID identifier) {
		super(identifier);

		stageMachine = new SequentialStageMachine(
				() -> new MinAndMaxJoiningStage<>(UztaPlayer.class, players, 1, 6, status),
				() -> new SetupStage(graph, players),
				() -> new FirstPlacementStage(graph, players),
				() -> new MainStage(graph, players),
				() -> new GameOverStage(status)
		);

		status.observe(this::fireEvent);
	}

	@NotNull
	@Override
	public String getGameName() {
		return UZTA;
	}

	@NotNull
	ModifiableUztaGraph getGraph() {
		return graph;
	}
}
