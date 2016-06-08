package jokatu.game.joining;

import jokatu.game.input.AbstractInputAcceptor;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Accepts {@link JoinInput}s from players and adds them to the game if there is enough room.  It fires a
 * {@link PlayerJoinedEvent} if the joining was successful.
 * @param <P> the type of {@link Player} to accept input from
 */
public class JoinInputAcceptor<P extends Player> extends AbstractInputAcceptor<JoinInput, P, PlayerJoinedEvent> {

	private final Class<P> playerClass;

	private final Map<String, P> players;

	/**
	 * The maximum number of players that can join the game needs before it starts.
	 */
	private final int maximum;

	public JoinInputAcceptor(@NotNull Class<P> playerClass, @NotNull Map<String, P> players, int maximum) {
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
			throw new GameFullException();
		}
		if (players.containsKey(inputter.getName())) {
			throw new PlayerAlreadyJoinedException();
		}
	}
}
