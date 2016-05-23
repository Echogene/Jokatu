package jokatu.game.player;

import jokatu.game.Game;
import org.jetbrains.annotations.NotNull;

/**
 * Given a username, this constructs a player for its respective game.
 * @param <P>
 */
public abstract class PlayerFactory<P extends Player> {

	@NotNull
	public abstract P produce(@NotNull Game<? extends P> game, @NotNull String username);
}
