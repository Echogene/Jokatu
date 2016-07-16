package jokatu.game.games.noughtsandcrosses.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.stage.GameOverStage;
import jokatu.game.stage.JoiningStage;
import jokatu.game.stage.machine.SequentialStageMachine;
import jokatu.game.status.StandardTextStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class NoughtsAndCrossesGame extends Game<NoughtsAndCrossesPlayer> {

	public static final String NOUGHTS_AND_CROSSES = "Noughts and crosses";

	private final StandardTextStatus status = new StandardTextStatus();

	NoughtsAndCrossesGame(GameID identifier) {
		super(identifier);

		stageMachine = new SequentialStageMachine(Arrays.asList(
				() -> new JoiningStage<>(NoughtsAndCrossesPlayer.class, players, 2, status),
				() -> new AllegianceStage(players.values(), status),
				() -> new InputStage(players.values(), status),
				() -> new GameOverStage(status)
		));

		status.observe(this::fireEvent);
	}

	@NotNull
	@Override
	public String getGameName() {
		return NOUGHTS_AND_CROSSES;
	}
}
