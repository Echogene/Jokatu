package jokatu.game.stage;

import jokatu.game.event.StageOverEvent;
import jokatu.game.input.InputAcceptor;
import jokatu.game.joining.*;
import jokatu.game.player.Player;
import jokatu.game.status.StandardTextStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class JoiningInputAcceptor<P extends Player> extends InputAcceptor<JoinInput, P> {

	private final Class<P> playerClass;

	private final Map<String, P> players;

	/**
	 * The number of players that need to join the game needs before it starts.
	 */
	private final int number;

	private final StandardTextStatus status;

	public JoiningInputAcceptor(@NotNull Class<P> playerClass, @NotNull Map<String, P> players, int number, @NotNull StandardTextStatus status) {
		this.playerClass = playerClass;
		this.players = players;
		this.number = number;
		this.status = status;

		status.setText(
				"Waiting for {0} player{1} to join.",
				number,
				number == 1 ? "" : "s"
		);
	}


	@NotNull
	@Override
	protected Class<JoinInput> getInputClass() {
		return JoinInput.class;
	}

	@NotNull
	@Override
	protected Class<P> getPlayerClass() {
		return playerClass;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull JoinInput input, @NotNull P inputter) throws Exception {
		checkCanJoin(inputter);
		players.put(inputter.getName(), inputter);
		if (players.size() == number) {
			fireEvent(new StageOverEvent());
		} else {
			int more = number - players.size();
			status.setText(
					"Waiting for {0} more player{1} to join.",
					more,
					more == 1 ? "" : "s"
			);
		}
		fireEvent(new PlayerJoinedEvent(inputter));
	}

	private void checkCanJoin(P inputter) throws CannotJoinGameException {
		if (players.size() > number - 1) {
			throw new GameFullException("No more players can join.");
		}
		if (players.containsKey(inputter.getName())) {
			throw new PlayerAlreadyJoinedException(inputter.getName() + " cannot join the game twice.");
		}
	}
}
