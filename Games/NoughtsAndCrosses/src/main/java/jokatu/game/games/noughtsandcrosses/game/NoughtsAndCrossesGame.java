package jokatu.game.games.noughtsandcrosses.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.JoiningStage;
import jokatu.game.Stage;
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

	private StandardTextStatus status = new StandardTextStatus("Waiting for two players to join");

	private final JoiningStage<NoughtsAndCrossesPlayer> joiningStage = new JoiningStage<>(NoughtsAndCrossesPlayer.class, players, 2, status);
	private final InputStage inputStage = new InputStage(players, status);

	// todo: the starting stage should be the joiningStage
	private Stage currentStage = inputStage;

	NoughtsAndCrossesGame(GameID identifier) {
		super(identifier);
		joiningStage.observe(this::fireEvent);
		inputStage.observe(this::fireEvent);
		status.observe(this::fireEvent);
	}

	@NotNull
	@Override
	protected Stage getCurrentStage() {
		return currentStage;
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
	public void advanceStage() {
		currentStage = inputStage;
	}
}
