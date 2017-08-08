package jokatu.game.stage;

import jokatu.game.event.GameEvent;
import jokatu.game.input.AbstractInputAcceptor;
import jokatu.game.input.Input;
import jokatu.game.player.Player;

/**
 * A {@link Stage} that only accepts one kind of {@link Input} from one kind of {@link Player}.
 */
public abstract class SingleInputStage<I extends Input, P extends Player, E extends GameEvent>
		extends AbstractInputAcceptor<I, P, E> implements Stage<E> {
}
