package jokatu.game.games.rockpaperscissors.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.player.StandardPlayer;
import jokatu.game.stage.GameOverStage;
import jokatu.game.stage.JoiningStage;
import jokatu.game.stage.machine.SequentialStageMachine;
import jokatu.game.status.StandardTextStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class RockPaperScissorsGame extends Game<StandardPlayer> {

	public static final String ROCK_PAPER_SCISSORS = "Rock/paper/scissors";

	private final StandardTextStatus status = new StandardTextStatus();

	RockPaperScissorsGame(GameID identifier) {
		super(identifier);

		stageMachine = new SequentialStageMachine(Arrays.asList(
				() -> new JoiningStage<>(StandardPlayer.class, players, 2, status),
				() -> new InputStage(players.values(), status),
				() -> new GameOverStage(status)
		));

		status.observe(this::fireEvent);
	}

	@NotNull
	@Override
	public String getGameName() {
		return ROCK_PAPER_SCISSORS;
	}
}
