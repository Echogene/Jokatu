package jokatu.game.turn;

import jokatu.game.event.GameEvent;
import jokatu.game.player.Player;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Manages whose turn it is.  Fires a {@link TurnChangedEvent} event when the turn changes.
 */
public class TurnManager<P extends Player> extends AbstractSynchronousObservable<GameEvent> {

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

	public void next() {
		int i = players.indexOf(currentPlayer);
		setCurrentPlayer(players.get((i + 1) % players.size()));
	}

	public P getCurrentPlayer() {
		return currentPlayer;
	}
}
