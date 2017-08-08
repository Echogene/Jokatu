package jokatu.game.stage;

import jokatu.game.Game;
import jokatu.game.event.GameEvent;
import jokatu.game.input.InputAcceptor;

/**
 * <p>{@link Game}s are logically partitioned into separate stages where different inputs are possible.</p>
 * <p>e.g.<ul>
 *     <li>Before a game starts, it tends to need some players to join, so most games have a {@link JoiningStage}.</li>
 *     <li>Some games have setup, where specific rules or aspects are decided, such as whether aces are high or low or
 *         where players should start on the board.</li>
 *     <li>After a game ends, no further input should be accepted, so most games have a {@link GameOverStage}.</li>
 * </ul></p>
 *
 * @param <E>
 */
public interface Stage<E extends GameEvent> extends InputAcceptor<E> {

	/**
	 * Start this stage.  This should be called after the stage is being observed so events fired from this method will
	 * be handled, unlike events fired during the constructor.
	 */
	default void start() {}
}
