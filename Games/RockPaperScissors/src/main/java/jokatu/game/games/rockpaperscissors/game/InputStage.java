package jokatu.game.games.rockpaperscissors.game;

import jokatu.game.event.StageOverEvent;
import jokatu.game.games.rockpaperscissors.input.RockPaperScissorsInput;
import jokatu.game.input.AnyEventInputAcceptor;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.player.StandardPlayer;
import jokatu.game.result.PlayerResult;
import jokatu.game.result.Result;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.set.bounded.BoundedPair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static jokatu.game.result.Result.DRAW;
import static jokatu.game.result.Result.WIN;

class InputStage extends AnyEventInputAcceptor<RockPaperScissorsInput, StandardPlayer> {

	private final BoundedPair<StandardPlayer> players;

	private final Map<StandardPlayer, RockPaperScissors> inputs = new ConcurrentHashMap<>();

	private final StandardTextStatus status;

	InputStage(Collection<StandardPlayer> players, StandardTextStatus status) {
		this.players = new BoundedPair<>(players);
		this.status = status;

		status.setText(
				"Waiting for inputs from {0} and {1}.",
				this.players.getFirst(),
				this.players.getSecond()
		);
	}

	@NotNull
	@Override
	protected Class<RockPaperScissorsInput> getInputClass() {
		return RockPaperScissorsInput.class;
	}

	@NotNull
	@Override
	protected Class<StandardPlayer> getPlayerClass() {
		return StandardPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull RockPaperScissorsInput input, @NotNull StandardPlayer inputter) throws Exception {

		if (!players.contains(inputter)) {
			throw new UnacceptableInputException("You can't input to a game you're not playing.");
		}

		if (inputs.containsKey(inputter)) {
			// Player has already chosen.
			throw new UnacceptableInputException("You can't change your mind.");
		}
		inputs.put(inputter, input.getChoice());
		if (inputs.size() == 2) {
			StandardPlayer player1 = players.getFirst();
			StandardPlayer player2 = players.getSecond();
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
			fireEvent(new StageOverEvent());
		} else {
			StandardPlayer other = players.getOther(inputter);
			assert other != null;
			status.setText("Waiting for input from {0}.", other.getName());
		}
	}
}
