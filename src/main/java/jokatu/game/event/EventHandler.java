package jokatu.game.event;

import jokatu.game.Game;

/**
 * @author steven
 */
public interface EventHandler {

	void handle(Game game, GameEvent e);
}
