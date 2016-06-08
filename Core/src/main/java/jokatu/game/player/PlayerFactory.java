package jokatu.game.player;

import jokatu.game.Game;
import org.jetbrains.annotations.NotNull;

/**
 * Creates players for users so they can interact with {@link Game}s.
 * @param <P> the type of {@link Player} to produce
 */
public interface PlayerFactory<P extends Player> {
	@NotNull
	P produce(@NotNull Game<?> game, @NotNull String username);
}
