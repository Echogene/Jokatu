package jokatu.game;

import jokatu.game.event.GameEvent;
import jokatu.game.input.Input;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.joining.CannotJoinGameException;
import jokatu.game.player.Player;
import jokatu.game.status.Status;
import jokatu.identity.Identifiable;
import ophelia.collections.BaseCollection;
import ophelia.event.observable.Observable;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public interface Game<P extends Player, I extends Input>
		extends Identifiable<GameID>, Observable<GameEvent> {

	@NotNull String getGameName();

	@NotNull BaseCollection<P> getPlayers();

	void join(@NotNull P player) throws CannotJoinGameException;

	@NotNull
	Status getStatus();

	void accept(@NotNull I input, @NotNull P player) throws UnacceptableInputException;

	default boolean hasPlayer(@NotNull P player) {
		return getPlayers().contains(player);
	}
}
