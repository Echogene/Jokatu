package jokatu.game.player;

import jokatu.game.Game;
import org.jetbrains.annotations.NotNull;

/**
 * Given a username, this constructs a player for its respective game.
 * @param <P>
 */
public interface PlayerFactory<P extends Player> {

	@NotNull
	P produce(@NotNull Game<? extends P> game, @NotNull String username);
}
