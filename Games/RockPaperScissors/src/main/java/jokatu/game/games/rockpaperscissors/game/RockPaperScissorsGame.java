package jokatu.game.games.rockpaperscissors.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.player.StandardPlayer;
import jokatu.game.stage.GameOverStage;
import jokatu.game.stage.JoiningStage;
import jokatu.game.status.StandardTextStatus;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RockPaperScissorsGame extends Game<StandardPlayer> {

	public static final String ROCK_PAPER_SCISSORS = "Rock/paper/scissors";

	private final Map<String, StandardPlayer> players = new HashMap<>();

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

	@Override
	public void advanceStageInner() {
		if (currentStage == null) {
			currentStage = new JoiningStage<>(StandardPlayer.class, players, 2, status);

		} else if (currentStage instanceof JoiningStage) {
			currentStage = new InputStage(players, status);

		} else {
			currentStage = new GameOverStage(status);
		}
	}
}
