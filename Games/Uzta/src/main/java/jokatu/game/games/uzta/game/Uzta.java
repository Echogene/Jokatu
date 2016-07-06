package jokatu.game.games.uzta.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.Node;
import jokatu.game.player.StandardPlayer;
import jokatu.game.stage.GameOverStage;
import jokatu.game.stage.JoiningStage;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.set.HashSet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Uzta extends Game<StandardPlayer> {

	public static final String UZTA = "Uzta";

	static final int DICE_SIZE = 12;

	private final List<Node> nodes = new ArrayList<>();
	private final Set<LineSegment> edges = new HashSet<>();

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

	@Override
	public void advanceStageInner() {
		if (currentStage == null) {
			// todo: accept more players
			currentStage = new JoiningStage<>(StandardPlayer.class, players, 1, status);

		} else if (currentStage instanceof JoiningStage) {
			currentStage = new SetupStage(nodes, edges, players);

		} else {
			currentStage = new GameOverStage(status);
		}
	}
}
