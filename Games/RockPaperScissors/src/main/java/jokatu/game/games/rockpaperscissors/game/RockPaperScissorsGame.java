package jokatu.game.games.rockpaperscissors.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.games.rockpaperscissors.input.RockPaperScissorsInputAcceptor;
import jokatu.game.games.rockpaperscissors.player.RockPaperScissorsPlayer;
import jokatu.game.input.Input;
import jokatu.game.input.InputAcceptor;
import jokatu.game.joining.JoinInputAcceptor;
import jokatu.game.player.Player;
import jokatu.game.status.StandardTextStatus;
import jokatu.game.status.Status;
import ophelia.collections.BaseCollection;
import ophelia.collections.UnmodifiableCollection;
import ophelia.collections.set.bounded.BoundedPair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class RockPaperScissorsGame extends Game<RockPaperScissorsPlayer> {

	public static final String ROCK_PAPER_SCISSORS = "Rock/paper/scissors";

	private final BoundedPair<RockPaperScissorsPlayer> players = new BoundedPair<>();

	private final StandardTextStatus status = new StandardTextStatus("Waiting for two players to join");

	private final List<InputAcceptor<? extends Input, ? extends Player>> inputAcceptors = Arrays.asList(
			new JoinInputAcceptor<>(RockPaperScissorsPlayer.class, players, status),
			new RockPaperScissorsInputAcceptor(players, status)
	);

	protected RockPaperScissorsGame(GameID identifier) {
		super(identifier);
		status.observe(this::fireEvent);
	}

	@org.jetbrains.annotations.NotNull
	@NotNull
	@Override
	public String getGameName() {
		return ROCK_PAPER_SCISSORS;
	}

	@org.jetbrains.annotations.NotNull
	@NotNull
	@Override
	public BoundedPair<RockPaperScissorsPlayer> getPlayers() {
		return players;
	}

	@org.jetbrains.annotations.NotNull
	@NotNull
	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	protected BaseCollection<InputAcceptor<? extends Input, ? extends Player>> getInputAcceptors() {
		return new UnmodifiableCollection<>(inputAcceptors);
	}
}
