package jokatu.game.player;

import org.jetbrains.annotations.NotNull;

/**
 * Given a username, this constructs a player for its respective game.
 * @param <P>
 */
public interface PlayerFactory<P extends Player> {

	@NotNull P produce(@NotNull String username);
}
