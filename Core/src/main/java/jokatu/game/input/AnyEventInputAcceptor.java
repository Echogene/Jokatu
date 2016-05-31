package jokatu.game.input;

import jokatu.game.event.GameEvent;
import jokatu.game.player.Player;

/**
 * These are {@link InputAcceptor}s that can fire any {@link GameEvent}.
 */
public abstract class AnyEventInputAcceptor<I extends Input, P extends Player>
		extends InputAcceptor<I, P, GameEvent> {}
