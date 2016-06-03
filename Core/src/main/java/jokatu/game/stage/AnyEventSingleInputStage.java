package jokatu.game.stage;

import jokatu.game.event.GameEvent;
import jokatu.game.input.Input;
import jokatu.game.player.Player;

/**
 * A {@link SingleInputStage} that can fire any event.
 */
public abstract class AnyEventSingleInputStage<I extends Input, P extends Player> extends SingleInputStage<I, P, GameEvent> {
}
