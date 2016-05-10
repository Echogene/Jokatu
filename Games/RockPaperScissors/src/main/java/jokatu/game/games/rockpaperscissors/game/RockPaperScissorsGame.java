package jokatu.game.games.rockpaperscissors.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.stage.JoiningStage;
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

	private final StandardTextStatus status = new StandardTextStatus();

	RockPaperScissorsGame(GameID identifier) {
		super(identifier);

		status.observe(this::fireEvent);
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
	public void advanceStageInner() {
		if (currentStage == null) {
			currentStage = new JoiningStage<>(RockPaperScissorsPlayer.class, players, 2, status);
		} else {
			currentStage = new InputStage(players, status);
		}
	}
}
