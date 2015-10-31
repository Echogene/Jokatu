package jokatu.game.event;

import jokatu.game.Game;
import org.springframework.stereotype.Component;

/**
 * An event handler that is annotated with {@link Component} is passed events when they are emitted from a game.
 * @author steven
 */
public interface EventHandler {

	void handle(Game game, GameEvent e);
}
