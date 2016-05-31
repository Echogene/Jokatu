package jokatu.game.stage;

import jokatu.game.input.InputAcceptor;
import jokatu.game.joining.*;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class JoiningInputAcceptor<P extends Player> extends InputAcceptor<JoinInput, P, PlayerJoinedEvent> {

	private final Class<P> playerClass;

	private final Map<String, P> players;

	/**
	 * The maximum number of players that can join the game needs before it starts.
	 */
	private final int maximum;

	JoiningInputAcceptor(@NotNull Class<P> playerClass, @NotNull Map<String, P> players, int maximum) {
		this.playerClass = playerClass;
		this.players = players;
		this.maximum = maximum;
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
		fireEvent(new PlayerJoinedEvent(inputter));
	}

	private void checkCanJoin(P inputter) throws CannotJoinGameException {
		if (players.size() > maximum - 1) {
			throw new GameFullException("No more players can join.");
		}
		if (players.containsKey(inputter.getName())) {
			throw new PlayerAlreadyJoinedException(inputter.getName() + " cannot join the game twice.");
		}
	}
}
