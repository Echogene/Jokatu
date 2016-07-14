package jokatu.game.games.uzta.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.games.uzta.graph.ModifiableUztaGraph;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.stage.GameOverStage;
import jokatu.game.stage.JoiningStage;
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

	@Override
	public void advanceStageInner() {
		if (currentStage == null) {
			// todo: accept more players
			currentStage = new JoiningStage<>(UztaPlayer.class, players, 1, status);

		} else if (currentStage instanceof JoiningStage) {
			currentStage = new SetupStage(graph, players);

		} else if (currentStage instanceof SetupStage) {
			currentStage = new FirstPlacementStage(graph, players);

		} else {
			currentStage = new GameOverStage(status);
		}
	}
}
