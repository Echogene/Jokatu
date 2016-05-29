package jokatu.game;

import jokatu.game.event.GameEvent;
import jokatu.game.input.Input;
import jokatu.game.player.Player;
import ophelia.event.observable.Observable;
import org.jetbrains.annotations.NotNull;

public interface Stage extends Observable<GameEvent> {
	void accept(@NotNull Input input, @NotNull Player player) throws Exception;
}
