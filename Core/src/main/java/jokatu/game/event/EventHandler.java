package jokatu.game.event;

import jokatu.game.Game;
import org.jetbrains.annotations.NotNull;

/**
 * Listens to events and handles them.
 */
public interface EventHandler {
	void handle(@NotNull Game<?> game, @NotNull GameEvent e);
}
