package jokatu.game.games.noughtsandcrosses.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.JoiningStage;
import jokatu.game.Stage;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.status.StandardTextStatus;
import jokatu.game.status.Status;
import ophelia.collections.set.bounded.BoundedPair;
import org.jetbrains.annotations.NotNull;

public class NoughtsAndCrossesGame extends Game<NoughtsAndCrossesPlayer> {

	public static final String NOUGHTS_AND_CROSSES = "Noughts and crosses";

	private final BoundedPair<NoughtsAndCrossesPlayer> players = new BoundedPair<>();;

	private StandardTextStatus status = new StandardTextStatus("Waiting for two players to join");

	private final JoiningStage<NoughtsAndCrossesPlayer> joiningStage = new JoiningStage<>(NoughtsAndCrossesPlayer.class, players, status);
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

	@NotNull
	@Override
	public BoundedPair<NoughtsAndCrossesPlayer> getPlayers() {
		return players;
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
