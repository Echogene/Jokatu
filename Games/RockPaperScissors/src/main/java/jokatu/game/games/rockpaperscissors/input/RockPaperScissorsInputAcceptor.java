package jokatu.game.games.rockpaperscissors.input;

import jokatu.game.games.rockpaperscissors.game.RockPaperScissors;
import jokatu.game.games.rockpaperscissors.player.RockPaperScissorsPlayer;
import jokatu.game.input.InputAcceptor;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.result.PlayerResult;
import jokatu.game.result.Result;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.set.bounded.BoundedPair;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static jokatu.game.result.Result.DRAW;
import static jokatu.game.result.Result.WIN;

public class RockPaperScissorsInputAcceptor extends InputAcceptor<RockPaperScissorsInput, RockPaperScissorsPlayer> {

	private final BoundedPair<RockPaperScissorsPlayer> players;

	private final Map<RockPaperScissorsPlayer, RockPaperScissors> inputs = new ConcurrentHashMap<>();

	private final StandardTextStatus status;

	public RockPaperScissorsInputAcceptor(Collection<RockPaperScissorsPlayer> players, StandardTextStatus status) {
		this.players = new BoundedPair<>(players);
		this.status = status;

		status.setText(
				"Waiting for inputs from {0} and {1}.",
				this.players.getFirst(),
				this.players.getSecond()
		);
	}

	@Override
	protected Class<RockPaperScissorsInput> getInputClass() {
		return RockPaperScissorsInput.class;
	}

	@Override
	protected Class<RockPaperScissorsPlayer> getPlayerClass() {
		return RockPaperScissorsPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(RockPaperScissorsInput input, RockPaperScissorsPlayer inputter) throws Exception {

		if (!players.contains(inputter)) {
			throw new UnacceptableInputException("You can't input to a game you're not playing.");
		}

		if (inputs.containsKey(inputter)) {
			// Player has already chosen.
			throw new UnacceptableInputException("You can't change your mind.");
		}
		inputs.put(inputter, input.getChoice());
		if (inputs.size() == 2) {
			RockPaperScissorsPlayer player1 = players.getFirst();
			RockPaperScissorsPlayer player2 = players.getSecond();
			Result result = inputs.get(player1).resultAgainst(inputs.get(player2));
			switch (result) {
				case WIN:
					fireEvent(new PlayerResult(WIN, singleton(player1)));
					break;
				case LOSE:
					fireEvent(new PlayerResult(WIN, singleton(player2)));
					break;
				default:
					fireEvent(new PlayerResult(DRAW, asList(player1, player2)));
			}
			status.setText("Game over.");
		} else {
			RockPaperScissorsPlayer other = players.getOther(inputter);
			assert other != null;
			status.setText("Waiting for input from {0}.", other.getName());
		}
	}
}
