package jokatu.game.input;

import jokatu.game.Game;
import jokatu.game.event.GameEvent;
import jokatu.game.player.Player;
import ophelia.collections.BaseCollection;
import ophelia.event.observable.Observable;
import org.jetbrains.annotations.NotNull;

/**
 * Accepts an {@link Input} from a {@link Player}, handles it, and may fire {@link GameEvent}s.  These are to be
 * created in the context of a single {@link Game} and will most likely have access to various parts of the game's
 * state.
 *
 * @param <E> the type of {@link GameEvent} that this may fire
 */
public interface InputAcceptor<E extends GameEvent> extends Observable<E> {

	@NotNull
	BaseCollection<Class<? extends Input>> getAcceptedInputs();

	void accept(@NotNull Input input, @NotNull Player player) throws Exception;
}
