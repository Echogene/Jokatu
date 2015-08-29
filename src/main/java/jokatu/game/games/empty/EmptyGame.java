package jokatu.game.games.empty;

import jokatu.game.AbstractGame;
import jokatu.game.GameID;
import jokatu.game.status.GameStatus;
import jokatu.game.event.GameEvent;
import jokatu.game.input.Input;
import jokatu.game.joining.CannotJoinGameException;
import jokatu.game.joining.GameFullException;
import jokatu.game.user.player.Player;
import ophelia.collections.set.EmptySet;
import org.jetbrains.annotations.NotNull;

import static jokatu.game.status.GameStatus.OVER;
import static ophelia.collections.set.EmptySet.emptySet;

/**
 * An zero-player game that does nothing, for testing.
 * @author Steven Weston
 */
public class EmptyGame extends AbstractGame<Player, Input> {

	public static final String EMPTY_GAME = "Empty game";

	public EmptyGame(GameID gameID) {
		super(gameID);
	}

	@NotNull
	@Override
	public String getGameName() {
		return EMPTY_GAME;
	}

	@NotNull
	@Override
	public EmptySet<Player> getPlayers() {
		return emptySet();
	}

	@Override
	public void join(@NotNull Player player) throws CannotJoinGameException {
		throw new GameFullException(getIdentifier(), "An empty game cannot have any players");
	}

	@NotNull
	@Override
	public GameStatus getStatus() {
		// An empty game is always over.
		return OVER;
	}

	@Override
	public void accept(@NotNull Input input, @NotNull Player player) {
		// Do nothing.
	}
}
