package jokatu.game.games.noughtsandcrosses.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.stage.GameOverStage;
import jokatu.game.stage.JoiningStage;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.status.StandardTextStatus;
import jokatu.game.status.Status;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class NoughtsAndCrossesGame extends Game<NoughtsAndCrossesPlayer> {

	public static final String NOUGHTS_AND_CROSSES = "Noughts and crosses";

	private final Map<String, NoughtsAndCrossesPlayer> players = new HashMap<>();

	private StandardTextStatus status = new StandardTextStatus();

	NoughtsAndCrossesGame(GameID identifier) {
		super(identifier);

		status.observe(this::fireEvent);
	}

	@NotNull
	@Override
	public String getGameName() {
		return NOUGHTS_AND_CROSSES;
	}

	@Nullable
	@Override
	public NoughtsAndCrossesPlayer getPlayerByName(@NotNull String name) {
		return players.get(name);
	}

	@NotNull
	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	protected void advanceStageInner() {
		if (currentStage == null) {
			currentStage = new JoiningStage<>(NoughtsAndCrossesPlayer.class, players, 2, status);

		} else if (currentStage instanceof JoiningStage) {
			currentStage = new AllegianceStage(players, status);

		} else if (currentStage instanceof AllegianceStage) {
			currentStage = new InputStage(players, status);

		} else {
			currentStage = new GameOverStage();
		}
	}
}
