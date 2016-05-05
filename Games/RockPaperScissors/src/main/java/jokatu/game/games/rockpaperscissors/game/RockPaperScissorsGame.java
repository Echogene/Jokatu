package jokatu.game.games.rockpaperscissors.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.JoiningStage;
import jokatu.game.Stage;
import jokatu.game.games.rockpaperscissors.player.RockPaperScissorsPlayer;
import jokatu.game.status.StandardTextStatus;
import jokatu.game.status.Status;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RockPaperScissorsGame extends Game<RockPaperScissorsPlayer> {

	public static final String ROCK_PAPER_SCISSORS = "Rock/paper/scissors";

	private final Map<String, RockPaperScissorsPlayer> players = new HashMap<>();

	private final StandardTextStatus status = new StandardTextStatus("Waiting for two players to join");

	private final JoiningStage<RockPaperScissorsPlayer> joiningStage = new JoiningStage<>(RockPaperScissorsPlayer.class, players, 2, status);
	private final InputStage inputStage = new InputStage(players, status);

	private Stage currentStage = joiningStage;

	RockPaperScissorsGame(GameID identifier) {
		super(identifier);
		// Forward events from everything.
		status.observe(this::fireEvent);
		joiningStage.observe(this::fireEvent);
		inputStage.observe(this::fireEvent);
	}

	@NotNull
	@Override
	protected Stage getCurrentStage() {
		return currentStage;
	}

	@NotNull
	@Override
	public String getGameName() {
		return ROCK_PAPER_SCISSORS;
	}

	@Nullable
	@Override
	public RockPaperScissorsPlayer getPlayerByName(@NotNull String name) {
		return players.get(name);
	}

	@NotNull
	Collection<RockPaperScissorsPlayer> getPlayers() {
		return players.values();
	}

	@NotNull
	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public void advanceStage() {
		// Move to the second stage.
		currentStage = inputStage;
	}
}
