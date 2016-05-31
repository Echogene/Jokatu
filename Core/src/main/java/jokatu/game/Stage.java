package jokatu.game;

import jokatu.game.event.GameEvent;
import jokatu.game.input.Input;
import jokatu.game.player.Player;
import ophelia.event.observable.Observable;
import org.jetbrains.annotations.NotNull;

public interface Stage<E extends GameEvent> extends Observable<E> {
	void accept(@NotNull Input input, @NotNull Player player) throws Exception;
}
