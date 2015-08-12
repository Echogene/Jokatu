package jokatu.game;

import jokatu.game.event.GameEvent;
import jokatu.game.input.Input;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.joining.CannotJoinGameException;
import jokatu.game.user.player.Player;
import jokatu.identity.Identifiable;
import ophelia.collections.BaseCollection;
import ophelia.event.observable.Observable;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public interface Game<P extends Player, I extends Input, C extends BaseCollection<P>, E extends GameEvent<P>>
		extends Identifiable<GameID>, Observable<E> {

	@NotNull String getGameName();

	@NotNull C getPlayers();

	void join(@NotNull P player) throws CannotJoinGameException;

	@NotNull Status getStatus();

	void accept(@NotNull I input, P player) throws UnacceptableInputException;

	default boolean hasPlayer(P player) {
		return getPlayers().contains(player);
	}
}
