package jokatu.game.games.rockpaperscissors.game;

import jokatu.game.games.rockpaperscissors.input.RockPaperScissorsInput;
import jokatu.game.input.AwaitingInputEvent;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.player.StandardPlayer;
import jokatu.game.result.PlayerResult;
import jokatu.game.result.Result;
import jokatu.game.stage.AnyEventSingleInputStage;
import jokatu.game.status.StandardTextStatus;
import jokatu.game.turn.NotAPlayerException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static jokatu.game.result.Result.DRAW;
import static jokatu.game.result.Result.WIN;

class InputStage extends AnyEventSingleInputStage<RockPaperScissorsInput, StandardPlayer> {

	private final List<StandardPlayer> players;

	private final Map<StandardPlayer, RockPaperScissors> inputs = new ConcurrentHashMap<>();

	private final StandardTextStatus status;

	InputStage(Collection<StandardPlayer> players, StandardTextStatus status) {
		this.players = new ArrayList<>(players);
		this.status = status;

		status.setText(
				"Waiting for inputs from {0} and {1}.",
				this.players.get(0),
				this.players.get(1)
		);
	}

	@Override
	public void start() {
		fireEvent(new AwaitingInputEvent(players));
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
			throw new NotAPlayerException();
		}

		if (inputs.containsKey(inputter)) {
			// Player has already chosen.
			throw new UnacceptableInputException("You can't change your mind.");
		}
		inputs.put(inputter, input.getChoice());
		if (inputs.size() == 2) {
			StandardPlayer player1 = players.get(0);
			StandardPlayer player2 = players.get(1);
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
		} else {
			StandardPlayer other = getOtherPlayer(inputter);
			status.setText("Waiting for input from {0}.", other.getName());
			fireEvent(new AwaitingInputEvent(other));
		}
	}

	private StandardPlayer getOtherPlayer(StandardPlayer inputter) {
		return players.get((players.indexOf(inputter) + 1) % 2);
	}
}
