package jokatu.game.rockpaperscissors;

import jokatu.game.AbstractGame;
import jokatu.game.GameID;
import jokatu.game.Status;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.joining.CannotJoinGameException;
import jokatu.game.joining.GameFullException;
import jokatu.game.result.PlayerResult;
import jokatu.game.result.Result;
import ophelia.collections.set.bounded.BoundedPair;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static jokatu.game.Status.*;
import static jokatu.game.result.Result.DRAW;
import static jokatu.game.result.Result.WIN;

public class RockPaperScissorsGame
		extends AbstractGame <
				RockPaperScissorsPlayer,
				RockPaperScissorsInput,
				BoundedPair<RockPaperScissorsPlayer>,
				PlayerResult
		> {

	public static final String ROCK_PAPER_SCISSORS = "Rock/paper/scissors";

	private final BoundedPair<RockPaperScissorsPlayer> players = new BoundedPair<>();

	private final Map<RockPaperScissorsPlayer, RockPaperScissors> inputs = new ConcurrentHashMap<>();

	private Status status;

	protected RockPaperScissorsGame(GameID identifier) {
		super(identifier);
		status = NOT_STARTED;
	}

	@Override
	public String getGameName() {
		return ROCK_PAPER_SCISSORS;
	}

	@Override
	public BoundedPair<RockPaperScissorsPlayer> getPlayers() {
		return players;
	}

	@Override
	public void join(RockPaperScissorsPlayer player) throws CannotJoinGameException {
		checkCanJoin();
		players.add(player);
		if (players.size() == 2) {
			status = IN_PROGRESS;
		}
	}

	private void checkCanJoin() throws CannotJoinGameException {
		if (players.size() > 1) {
			throw new GameFullException("Rock-paper-scissors supports two players");
		}
		if (status != NOT_STARTED) {
			throw new CannotJoinGameException("Cannot join rock-paper-scissors after it has started");
		}
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public void accept(RockPaperScissorsInput input) throws UnacceptableInputException {
		RockPaperScissorsPlayer inputter = input.getPlayer();
		if (inputs.containsKey(inputter)) {
			// Player has already chosen.
			throw new UnacceptableInputException("You can't change your mind");
		}
		inputs.put(inputter, input.getChoice());
		if (inputs.size() == 2) {
			RockPaperScissorsPlayer player1 = players.getFirst();
			RockPaperScissorsPlayer player2 = players.getSecond();
			Result result = inputs.get(player1).resultAgainst(inputs.get(player2));
			switch (result) {
				case WIN:
					fireEvent(new PlayerResult(WIN, singleton(player1)));
				case LOSE:
					fireEvent(new PlayerResult(WIN, singleton(player2)));
				default:
					fireEvent(new PlayerResult(DRAW, asList(player1, player2)));
			}
			status = OVER;
		}
	}
}
