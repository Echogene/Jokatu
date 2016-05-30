package jokatu.game.turn;

import jokatu.game.player.Player;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Manages whose turn it is.  Fires a {@link TurnChangedEvent} event when the turn changes.
 */
public class TurnManager<P extends Player> extends AbstractSynchronousObservable<TurnChangedEvent> {

	private final List<P> players;

	private P currentPlayer;

	public TurnManager(@NotNull List<P> players, @NotNull P startingPlayer) {
		assert players.contains(startingPlayer);
		this.players = players;
		this.currentPlayer = startingPlayer;
	}

	private void setCurrentPlayer(@NotNull P newPlayer) {
		P oldPlayer = currentPlayer;
		currentPlayer = newPlayer;
		fireEvent(new TurnChangedEvent(oldPlayer, currentPlayer));
	}

	/**
	 * Assert that the current player is the given player.
	 * @param player this should be the current player
	 * @throws NotYourTurnException if the given player is not the current player
	 */
	public void assertCurrentPlayer(@NotNull P player) throws NotYourTurnException {
		if (player != currentPlayer) {
			throw new NotYourTurnException(currentPlayer);
		}
	}

	/**
	 * Pass the turn onto the next player.
	 */
	public void next() {
		int i = players.indexOf(currentPlayer);
		setCurrentPlayer(players.get((i + 1) % players.size()));
	}

	@NotNull
	public P getCurrentPlayer() {
		return currentPlayer;
	}
}
