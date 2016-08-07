package jokatu.game.turn;

import jokatu.game.player.Player;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Manages whose turn it is.  Fires a {@link TurnChangedEvent} event when the turn changes.
 */
public class TurnManager<P extends Player> extends AbstractSynchronousObservable<TurnChangedEvent> {

	private final List<P> players;

	private P currentPlayer;

	public TurnManager(@NotNull List<P> players) {
		this.players = players;
	}

	public void setCurrentPlayer(@NotNull P newPlayer) {
		P oldPlayer = currentPlayer;
		currentPlayer = newPlayer;
		fireEvent(new TurnChangedEvent(oldPlayer, currentPlayer));
	}

	/**
	 * Assert that the given player is both an actual player and the current player.
	 * @param player this should be the current player
	 * @throws NotYourTurnException if the given player is not the current player
	 */
	public void assertCurrentPlayer(@NotNull P player) throws NotYourTurnException, NotAPlayerException {
		if (!players.contains(player)) {
			throw new NotAPlayerException();
		}
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

	/**
	 * Pass the turn onto the previous player.
	 */
	public void previous() {
		int i = players.indexOf(currentPlayer);
		setCurrentPlayer(players.get((i + players.size() - 1) % players.size()));
	}

	public void playAgain() {
		setCurrentPlayer(currentPlayer);
	}

	@Nullable
	public P getCurrentPlayer() {
		return currentPlayer;
	}
}
