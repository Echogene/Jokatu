package jokatu.game.input;

import jokatu.game.event.GameEvent;
import jokatu.game.player.Player;

/**
 * These are {@link AbstractInputAcceptor}s that can fire any {@link GameEvent}.
 */
public abstract class AnyEventInputAcceptor<I extends Input, P extends Player>
		extends AbstractInputAcceptor<I, P, GameEvent> {}
